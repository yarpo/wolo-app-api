package pl.pjwstk.woloappapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import pl.pjwstk.woloappapi.model.entities.Event;
import pl.pjwstk.woloappapi.repository.EventRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class EventServiceTests {
    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testGetEventById() {
        Long eventId = 1L;
        Event mockEvent = new Event();
        mockEvent.setId(eventId);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(mockEvent));

        Event result = eventService.getEventById(eventId);

        assertNotNull(result);
        assertEquals(eventId, result.getId());
    }

    @Test
    void testGetAllEvents() {
        List<Event> mockEvents = new ArrayList<>();
        mockEvents.add(new Event());
        mockEvents.add(new Event());
        when(eventRepository.findAll()).thenReturn(mockEvents);

        List<Event> result = eventService.getAllEvents();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testGetEventsByUser() {
        Long userId = 123L;
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(new Event());
        expectedEvents.add(new Event());
        when(eventRepository.findEventsByUserId(userId)).thenReturn(expectedEvents);

        List<Event> result = eventService.getEventsByUser(userId);

        assertEquals(expectedEvents.size(), result.size());
    }

    @Test
    void testGetUpcomingEvents() {
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(new Event());
        expectedEvents.add(new Event());
        when(eventRepository.findAllNotBeforeNow()).thenReturn(expectedEvents);

        List<Event> result = eventService.getUpcomingEvents();

        assertEquals(expectedEvents.size(), result.size());
    }


    @Test
    void testGetTheyNeedYouList_EventsFound() {
        var thresholdDate = LocalDate.now().minusDays(5);
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(new Event());
        when(eventRepository.findEventsForTheyNeedYou(any(LocalDate.class))).thenReturn(expectedEvents);

        List<Event> result = eventService.getTheyNeedYouList();

        assertEquals(expectedEvents.size(), result.size());
        verify(eventRepository, times(1)).findEventsForTheyNeedYou(thresholdDate);
        verify(eventRepository, never()).findNearestEventsSortedByDate(any(LocalDate.class), any(PageRequest.class));
}

    @Test
    void testGetTheyNeedYouList_NoEventsFound() {
        List<Event> nearestEvents = new ArrayList<>();
        nearestEvents.add(new Event());
        nearestEvents.add(new Event());
        nearestEvents.add(new Event());
        nearestEvents.add(new Event());
        nearestEvents.add(new Event());
        when(eventRepository.findNearestEventsSortedByDate(any(LocalDate.class), any(PageRequest.class)))
                .thenReturn(nearestEvents);

        List<Event> result = eventService.getTheyNeedYouList();

        assertEquals(nearestEvents.size(), result.size());
        verify(eventRepository, times(1)).findNearestEventsSortedByDate(LocalDate.now(), PageRequest.of(0, 5));
    }
}
