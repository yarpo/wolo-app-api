package pl.pjwstk.woloappapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.Event;
import pl.pjwstk.woloappapi.repository.EventRepository;
import pl.pjwstk.woloappapi.service.EventService;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {
    @Mock private EventRepository eventRepository;
    @InjectMocks private EventService eventService;
    /*
    @Test
    public void testGetAllEvents() {
        List<Event> events = new ArrayList<>();
        events.add(createValidEvent(1L, "Event 1"));
        events.add(createValidEvent(2L, "Event 2"));
        when(eventRepository.findAll()).thenReturn(events);

        List<Event> result = eventService.getAllEvents();

        assertEquals(events, result);
        verify(eventRepository, times(1)).findAll();
    }
     */

    /*
    @Test
    public void testGetEventById_ExistingId() {
        Long eventId = 1L;
        Event event = createValidEvent(eventId, "Event 1");
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        Event result = eventService.getEventById(eventId);

        assertEquals(event, result);
        verify(eventRepository, times(1)).findById(eventId);
    }

     */

    @Test
    public void testGetEventById_NonExistingId() {
        Long eventId = 1L;
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> eventService.getEventById(eventId));
        verify(eventRepository, times(1)).findById(eventId);
    }

    /*
    @Test
    public void testCreateEvent_ValidEvent() {
        Event event = createValidEvent(1L, "Event 1");

        eventService.createEvent(event);

        verify(eventRepository, times(1)).save(event);
    }
    */

    @Test
    public void testDeleteEvent_ExistingId() {
        Long eventId = 1L;
        when(eventRepository.existsById(eventId)).thenReturn(true);

        eventService.deleteEvent(eventId);

        verify(eventRepository, times(1)).existsById(eventId);
        verify(eventRepository, times(1)).deleteById(eventId);
    }

    @Test
    public void testDeleteEvent_NonExistingId() {
        Long eventId = 1L;
        when(eventRepository.existsById(eventId)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> eventService.deleteEvent(eventId));
        verify(eventRepository, times(1)).existsById(eventId);
        verify(eventRepository, never()).deleteById(eventId);
    }

    /*
    @Test
    public void testFilterEvents() {
        String[] localizations = {"TestLocalization1", "TestLocalization2"};
        LocalDate startDate = LocalDate.of(2023, 3, 17);
        LocalDate endDate = LocalDate.of(2023, 3, 18);
        Long category = 1L;
        Long organizer = 2L;
        Integer ageRestriction = 18;
        boolean isPeselValid = true;
        List<Event> events = new ArrayList<>();
        Event event1 = createValidEvent(1L, "Event 1");
        Event event2 = createValidEvent(2L, "Event 2");
        events.add(event1);
        events.add(event2);

        when(eventRepository.findAllByFilter(localizations, startDate, endDate, category, organizer, ageRestriction, isPeselValid))
                .thenReturn(events);

        List<Event> result = eventService.filterEvents(localizations, startDate, endDate, category, organizer, ageRestriction, isPeselValid);

        assertEquals(events, result);
        verify(eventRepository, times(1)).findAllByFilter(localizations, startDate, endDate, category, organizer, ageRestriction, isPeselValid);
    }
    */
    /*
    private Event createValidEvent(Long id, String name) {
        Event event = new Event();
        event.setId(id);
        event.setName(name);
        event.setDescription("Event description");
        event.setOrganisation(new Organisation());
        event.setCategories(new HashSet<>());
        event.setPeselVerificationRequired(true);
        event.setAgreementNeeded(false);
        return event;
    }
    */

    private Event createInvalidEvent() {
        Event event = new Event();
        event.setName("");

        return event;
    }
}
