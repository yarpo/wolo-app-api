package pl.pjwstk.woloappapi.service;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pjwstk.woloappapi.model.EventEditRequestDto;
import pl.pjwstk.woloappapi.model.EventRequestDto;
import pl.pjwstk.woloappapi.model.entities.CategoryToEvent;
import pl.pjwstk.woloappapi.model.entities.Event;
import pl.pjwstk.woloappapi.model.entities.Shift;
import pl.pjwstk.woloappapi.model.translation.EventTranslationResponse;
import pl.pjwstk.woloappapi.repository.EventRepository;
import pl.pjwstk.woloappapi.repository.ShiftToUserRepository;
import pl.pjwstk.woloappapi.utils.EmailUtil;
import pl.pjwstk.woloappapi.utils.EventMapper;
import pl.pjwstk.woloappapi.utils.EventUpdater;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final EventUpdater eventUpdater;
    private final OrganisationService organisationService;
    private final CategoryService categoryService;
    private final CategoryToEventService categoryToEventService;
    private final ShiftToUserRepository shiftToUserRepository;
    private final EmailUtil emailUtil;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Event id not found!"));
    }

    @Transactional
    public void createEvent(EventTranslationResponse translation, EventRequestDto eventDto) {
        var event = eventMapper.toEvent(eventDto, translation)
                .organisation(organisationService.getOrganisationById(eventDto.getOrganisationId()))
                .build();

        var shifts = new ArrayList<Shift>();
        eventDto.getShifts().forEach(s -> {
                    var shift = eventMapper.toShift(s, eventDto, translation)
                            .event(event)
                            .build();
                    shifts.add(shift);
                });
        event.setShifts(shifts);
        var savedEvent = eventRepository.save(event);

        eventDto.getCategories()
                .forEach(categoryId -> {
                            var categoryToEvent = new CategoryToEvent();
                            categoryToEvent.setCategory(categoryService.getCategoryById(categoryId));
                            categoryToEvent.setEvent(savedEvent);
                            categoryToEventService.createCategoryToEvent(categoryToEvent);
                });
    }

    @Transactional
    public void updateEvent(EventEditRequestDto eventDto, Long id) {
        eventUpdater.update(eventDto, id);
    }

    @Transactional
    public void deleteEvent(Long id) {
        eventRepository.findById(id).ifPresent(e ->{
            if (e.getDate().isAfter(LocalDate.now())) {
                e.getCategories().forEach(ctu -> categoryToEventService.deleteCategoryToEvent(ctu.getId()));
                e.getShifts().forEach(s -> s.getShiftToUsers()
                                            .forEach(stu -> {
                                                try {
                                                    emailUtil.sendDeleteEventMessage(stu.getUser().getEmail(), id);
                                                } catch (MessagingException ex) {
                                                    throw new RuntimeException(ex);
                                                }
                                                shiftToUserRepository.delete(stu);
                                            }));
                eventRepository.deleteById(id);
            }
        });
    }

    public List<Event> filterEvents(
            String[] localizations,
            LocalDate startDate,
            LocalDate endDate,
            Long[] categories,
            Long organizer,
            Integer ageRestriction,
            Boolean isPeselVerificationRequired,
            Boolean showWithAvailableCapacity) {
        return eventRepository.findAllByFilter(
                localizations,
                startDate,
                endDate,
                categories,
                organizer,
                ageRestriction,
                isPeselVerificationRequired,
                showWithAvailableCapacity);
    }

    public List<Event> getEventsByUser(Long id) {
        return eventRepository.findEventsByUserId(id);
    }

    public List<Event> getUpcomingEvents() {
        return eventRepository.findAllNotBeforeNow();
    }

    public List<Event> getTheyNeedYouList() {
        var thresholdDate = LocalDate.now().plusDays(5);

        List<Event> eventsTheyNeedYou = eventRepository.findEventsForTheyNeedYou(thresholdDate);
        if (!eventsTheyNeedYou.isEmpty()) {
            return eventsTheyNeedYou;
        } else {
            var pageable = PageRequest.of(0, 5);
            return eventRepository.findNearestEventsSortedByDate(pageable);
        }
    }
}
