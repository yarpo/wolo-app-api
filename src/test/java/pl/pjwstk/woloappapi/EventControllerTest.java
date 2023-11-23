package pl.pjwstk.woloappapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.pjwstk.woloappapi.controller.EventController;
import pl.pjwstk.woloappapi.model.Event;
import pl.pjwstk.woloappapi.service.EventService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventControllerTest {
    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    @Test
    public void testGetEvents() {
        List<Event> events = new ArrayList<>();
        events.add(createEvent(1L, "Event 1", "Description 1", true, false));
        events.add(createEvent(2L, "Event 2", "Description 2", false, true));
        when(eventService.getAllEvents()).thenReturn(events);

        ResponseEntity<List<Event>> responseEntity = eventController.getEvents();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(events, responseEntity.getBody());
        verify(eventService, times(1)).getAllEvents();
    }

    @Test
    public void testGetEventById_ExistingId() {
        Long eventId = 1L;
        Event event = createEvent(eventId, "Event 1", "Description 1", true, false);
        when(eventService.getEventById(eventId)).thenReturn(event);

        ResponseEntity<Event> responseEntity = eventController.getEventById(eventId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(event, responseEntity.getBody());
        verify(eventService, times(1)).getEventById(eventId);
    }

    /*
    @Test
    public void testAddEvent_ValidEvent() {
        Event eventToAdd = createEvent(1L, "Event 1", "Description 1", true, false);

        ResponseEntity<HttpStatus> responseEntity = eventController.addEvent(eventToAdd);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(eventService, times(1)).createEvent(eventToAdd);
    }

     */

    @Test
    public void testFilterEvents_NoFilters() {
        List<Event> events = new ArrayList<>();
        events.add(createEvent(1L, "Event 1", "Description 1", true, false));
        events.add(createEvent(2L, "Event 2", "Description 2", false, true));

        when(eventService.filterEvents(null, null, null,
                null, null, null, false)).thenReturn(events);

        ResponseEntity<List<Event>> responseEntity = eventController.filterEvents(
                null, null, null, null, null, null, false);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(events, responseEntity.getBody());
        verify(eventService, times(1)).filterEvents(null, null, null, null, null, null, false);
    }

    @Test
    public void testFilterEvents_NoResults() {
        List<Event> events = new ArrayList<>();
        when(eventService.filterEvents(any(), any(), any(), any(), any(), any(), any())).thenReturn(events);

        ResponseEntity<List<Event>> responseEntity = eventController.filterEvents(
                new String[]{"Localization1"},
                LocalDate.of(2023, 3, 17),
                LocalDate.of(2023, 3, 18),
                1L,
                2L,
                18,
                true
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(events, responseEntity.getBody());
    }


    private Event createEvent(Long id, String name, String description, boolean isPeselVerificationRequired, boolean isAgreementNeeded) {
        Event event = new Event();
        event.setId(id);
        event.setName(name);
        event.setDescription(description);
        event.setPeselVerificationRequired(isPeselVerificationRequired);
        event.setAgreementNeeded(isAgreementNeeded);
        return event;
    }

}
