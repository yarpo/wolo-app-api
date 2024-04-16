package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pjwstk.woloappapi.model.EventRequestDto;
import pl.pjwstk.woloappapi.model.entities.CategoryToEvent;
import pl.pjwstk.woloappapi.model.entities.Event;
import pl.pjwstk.woloappapi.model.entities.Shift;
import pl.pjwstk.woloappapi.repository.EventRepository;
import pl.pjwstk.woloappapi.utils.EventMapper;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final DistrictService districtService;
    private final OrganisationService organisationService;
    private final CategoryService categoryService;
    private final CategoryToEventService categoryToEventService;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Event id not found!"));
    }

    @Transactional
    public void createEvent(EventRequestDto eventDto) {
        var event = eventMapper.toEvent(eventDto)
                .organisation(organisationService.getOrganisationById(eventDto.getOrganisationId()))
                .build();

        var shifts = new ArrayList<Shift>();
        eventDto.getShifts().forEach(s -> {
                    var shift = eventMapper.toShift(s)
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
    public void updateEvent(EventRequestDto eventDto, Long id) {
        var city = districtService.getDistrictById(eventDto.getShifts().get(0).getDistrictId()).getCity();
        Event event = eventRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Event id not found!"));
        event.setName(eventDto.getName());
        event.setDescription(eventDto.getDescription());
        event.setPeselVerificationRequired(eventDto.isPeselVerificationRequired());
        event.setAgreementNeeded(eventDto.isAgreementNeeded());
        event.setImageUrl(eventDto.getImageUrl());
        event.setCity(city);

        updateEventCategories(event, eventDto);
        updateEventShifts(event, eventDto);

        eventRepository.save(event);
    }

    private void updateEventCategories(Event event, EventRequestDto eventDto) {
        event.getCategories().removeIf(cte ->!eventDto.getCategories()
                                        .contains(cte.getCategory().getId()));

        eventDto.getCategories()
                .stream()
                .filter(categoryId ->event.getCategories().stream()
                                        .noneMatch(categoryToEvent ->
                                                categoryId.equals(categoryToEvent
                                                        .getCategory()
                                                        .getId())))
                .map(categoryService::getCategoryById)
                .map(category -> {
                            CategoryToEvent newCategoryToEvent = new CategoryToEvent();
                            newCategoryToEvent.setCategory(category);
                            newCategoryToEvent.setEvent(event);
                            return newCategoryToEvent;
                        })
                .forEach(event.getCategories()::add);
    }

    private void updateShiftAddress(Shift shift, Shift newShift) {
        var address = shift.getAddress();
        var newAddress =newShift.getAddress();
        address.setDistrict(newAddress.getDistrict());
        address.setStreet(newAddress.getStreet());
        address.setHomeNum(newAddress.getHomeNum());
    }

    private void updateEventShifts(Event event, EventRequestDto eventDto) {
        var newShifts = eventDto.getShifts().stream()
                .map(s -> eventMapper.toShift(s)
                        .event(event)
                        .build())
                .toList();
        var newShiftIds = newShifts.stream().map(Shift::getId).toList();

        event.getShifts().retainAll(event.getShifts()
                .stream().filter(s -> newShiftIds.contains(s.getId())).toList());

        newShifts.forEach(ns -> {
                    if (ns.getId() == null) {
                        event.getShifts().add(ns);
                    } else {
                        event.getShifts().stream()
                                .filter(s -> s.getId().equals(ns.getId()))
                                .findFirst()
                                .ifPresent(
                                        existingShift ->
                                                updateShiftFields(existingShift, ns));
                    }
                });
    }
    private void updateShiftFields(Shift shift, Shift newShift) {
        shift.setStartTime(newShift.getStartTime());
        shift.setEndTime(newShift.getEndTime());
        shift.setDate(newShift.getDate());
        shift.setLeaderRequired(newShift.isLeaderRequired());
        shift.setCapacity(newShift.getCapacity());
        shift.setRequiredMinAge(newShift.getRequiredMinAge());
        shift.setShiftDirections(newShift.getShiftDirections());
        updateShiftAddress(shift, newShift);
    }
    @Transactional
    public void deleteEvent(Long id) {
        eventRepository.findById(id).ifPresent(e -> eventRepository.deleteById(id));
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
