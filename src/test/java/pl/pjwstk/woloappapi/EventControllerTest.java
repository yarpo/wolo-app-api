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
import pl.pjwstk.woloappapi.utils.NotFoundException;

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

    @Test
    public void testGetEventById_NonExistingId() {
        Long eventId = 1L;
        when(eventService.getEventById(eventId)).thenThrow(new NotFoundException("Event id not found!"));

        ResponseEntity<Event> responseEntity = eventController.getEventById(eventId);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(eventService, times(1)).getEventById(eventId);
    }

    @Test
    public void testAddEvent_ValidEvent() {
        Event eventToAdd = createEvent(1L, "Event 1", "Description 1", true, false);

        ResponseEntity<HttpStatus> responseEntity = eventController.addEvent(eventToAdd);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(eventService, times(1)).createEvent(eventToAdd);
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
