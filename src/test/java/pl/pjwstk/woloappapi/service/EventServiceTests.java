package pl.pjwstk.woloappapi.service;

import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pl.pjwstk.woloappapi.model.EventEditRequestDto;
import pl.pjwstk.woloappapi.model.EventRequestDto;
import pl.pjwstk.woloappapi.model.ShiftRequestDto;
import pl.pjwstk.woloappapi.model.entities.*;
import pl.pjwstk.woloappapi.model.translation.EventTranslationResponse;
import pl.pjwstk.woloappapi.repository.EventRepository;
import pl.pjwstk.woloappapi.repository.ShiftToUserRepository;
import pl.pjwstk.woloappapi.utils.EmailUtil;
import pl.pjwstk.woloappapi.utils.EventMapper;
import pl.pjwstk.woloappapi.utils.EventUpdater;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class EventServiceTests {
    @Mock
    private EventRepository eventRepository;

    @Mock
    private CategoryService categoryService;

    @Mock
    private EventMapper eventMapper;

    @Mock
    private EventUpdater eventUpdater;

    @Mock
    private CategoryToEventService categoryToEventService;

    @Mock
    private OrganisationService organisationService;

    @Mock
    private ShiftToUserRepository shiftToUserRepository;

    @Mock
    private EmailUtil emailUtil;

    @InjectMocks
    private EventService eventService;

    private Event.EventBuilder eventBuilder;

    @BeforeEach
    void setUp() {
        var shift = new Shift();
        shift.setId(1L);
        eventBuilder = Event.builder();
        eventBuilder.id(1L)
                    .namePL("Test Event")
                .shifts(List.of(shift));
    }


    @Test
    public void testGetEventById() {
        Long eventId = 1L;
        Event expectedEvent = new Event();
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(expectedEvent));

        Event actualEvent = eventService.getEventById(eventId);

        assertNotNull(actualEvent);
        assertEquals(expectedEvent, actualEvent);
    }

    @Test
    public void testGetEventByIdEventNotExists() {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> eventService.getEventById(1L));

        verify(eventRepository, times(1)).findById(1L);
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
    void testGetTheyNeedYouListEventsFound() {
        var thresholdDate = LocalDate.now().plusDays(5);
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(new Event());
        when(eventRepository.findEventsForTheyNeedYou(any(LocalDate.class))).thenReturn(expectedEvents);

        List<Event> result = eventService.getTheyNeedYouList();

        assertEquals(expectedEvents.size(), result.size());
        verify(eventRepository, times(1)).findEventsForTheyNeedYou(thresholdDate);
        verify(eventRepository, never()).findNearestEventsSortedByDate(any(PageRequest.class));
}

    @Test
    void testGetTheyNeedYouListNoEventsFound() {
        List<Event> nearestEvents = new ArrayList<>();
        nearestEvents.add(new Event());
        nearestEvents.add(new Event());
        nearestEvents.add(new Event());
        nearestEvents.add(new Event());
        nearestEvents.add(new Event());
        when(eventRepository.findNearestEventsSortedByDate(any(Pageable.class)))
                .thenReturn(nearestEvents);

        var result = eventService.getTheyNeedYouList();

        assertEquals(nearestEvents.size(), result.size());
        verify(eventRepository, times(1)).findNearestEventsSortedByDate(PageRequest.of(0, 5));
    }

    @Test
    public void testCreateEvent() {
        var translation = new EventTranslationResponse();
        var eventDto = EventRequestDto.builder()
                .categories(List.of(1L))
                .organisationId(1L)
                .build();
        var shift = new ShiftRequestDto();
        eventDto.setShifts(List.of(shift));
        var shiftBuilder = Shift.builder()
                .event(eventBuilder.build());
        var organisation = Organisation.builder()
                        .id(1L)
                        .build();
        var category = Category.builder()
                        .id(1L)
                        .build();
        when(eventMapper.toEvent(any(), any())).thenReturn(eventBuilder);
        when(eventMapper.toShift(any(), any(), any())).thenReturn(shiftBuilder);
        when(organisationService.getOrganisationById(anyLong())).thenReturn(organisation);
        when(categoryService.getCategoryById(anyLong())).thenReturn(category);

        eventService.createEvent(translation, eventDto);

        verify(eventRepository, times(1)).save(any(Event.class));
        verify(categoryToEventService, times(eventDto.getCategories().size())).createCategoryToEvent(any(CategoryToEvent.class));
    }

    @Test
    public void testUpdateEvent() {
        EventEditRequestDto eventDto = new EventEditRequestDto();
        Long eventId = 1L;
        eventDto.setMailSend(true);
        eventService.updateEvent(eventDto, eventId);

        verify(eventUpdater, times(1)).update(eventDto, eventId);
    }

    @Test
    void testDeleteEvent_FutureDate() throws MessagingException {
        long eventId = 1L;
        Event event = new Event();
        event.setDate(LocalDate.now().plusDays(1));

        CategoryToEvent category = new CategoryToEvent();
        category.setId(123L);
        event.setCategories(Collections.singletonList(category));
        var user = User.builder().email("email").build();
        var stu = new ShiftToUser();
        stu.setUser(user);
        var shift = Shift.builder().shiftToUsers(List.of(stu)).build();
        event.setShifts(List.of(shift));

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        doNothing().when(emailUtil).sendDeleteEventMessage(anyString(), anyLong());
        doNothing().when(shiftToUserRepository).delete(any());

        eventService.deleteEvent(eventId);

        verify(eventRepository, times(1)).findById(eventId);
        verify(categoryToEventService, times(event.getCategories().size())).deleteCategoryToEvent(category.getId());
        verify(emailUtil, times(event.getShifts().size())).sendDeleteEventMessage(anyString(), anyLong());
        verify(shiftToUserRepository, times(event.getShifts().size())).delete(any());
        verify(eventRepository, times(1)).deleteById(eventId);
    }
    @Test
    void testDeleteEvent_PastDate() throws MessagingException {
        var eventId = 1L;
        var event = new Event();
        event.setDate(LocalDate.now().minusDays(1)); // Past date
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        eventService.deleteEvent(eventId);

        verify(eventRepository, times(1)).findById(eventId);
        verify(categoryToEventService, never()).deleteCategoryToEvent(anyLong());
        verify(emailUtil, never()).sendDeleteEventMessage(anyString(), eq(eventId));
        verify(shiftToUserRepository, never()).delete(any());
        verify(eventRepository, never()).deleteById(eventId);
    }

    @Test
    void testDeleteEvent_EventNotFound() {
        var eventId = 1L;
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        eventService.deleteEvent(eventId);

        verify(eventRepository, times(1)).findById(eventId);
        verifyNoMoreInteractions(categoryToEventService, shiftToUserRepository, emailUtil, eventRepository);
    }


}
