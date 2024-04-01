package pl.pjwstk.woloappapi.service;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import pl.pjwstk.woloappapi.model.entities.Event;
import pl.pjwstk.woloappapi.repository.EventRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class EventServiceTests {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    public void testGetAllUsers(){
        Event event1 = new Event();
        Event event2 = new Event();

        when(eventRepository.findAll()).thenReturn(Arrays.asList(event1, event2));

        List<Event> actualEvents = eventService.getAllEvents();

        assertEquals(2, actualEvents.size());
    }
}
