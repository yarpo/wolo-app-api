package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pjwstk.woloappapi.model.EventRequestDto;
import pl.pjwstk.woloappapi.model.entities.*;
import pl.pjwstk.woloappapi.repository.EventRepository;
import pl.pjwstk.woloappapi.utils.EventMapper;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final DistrictService districtService;
    private final OrganisationService organisationService;
    private final AddressToEventSevice addressToEventService;
    private final CategoryService categoryService;
    private final ShiftService shiftService;
    private final CategoryToEventService categoryToEventService;
    private final AddressService addressService;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Event id not found!"));
    }

    @Transactional
    public void createEvent(EventRequestDto dtoEvent) {
        Event event = eventMapper.toEvent(dtoEvent)
                .organisation(organisationService.getOrganisationById(dtoEvent.getOrganisationId())).build();

        Address address = eventMapper.toAddress(dtoEvent)
                .district(districtService.getDistrictById(dtoEvent.getDistrictId()))
                .build();

        addressService.createAddress(address);

        eventRepository.save(event);

        AddressToEvent addressToEvent = new AddressToEvent(event, address);

        addressToEventService.createAddressToEvent(addressToEvent);

        List<Shift> shifts = eventMapper.toShifts(dtoEvent.getShifts());
        shifts.forEach(
                shift -> {
                    shift.setAddressToEvent(addressToEvent);
                    shiftService.createShift(shift);
                });

        dtoEvent.getCategories()
                .forEach(
                        categoryId -> {
                            CategoryToEvent categoryToEvent = new CategoryToEvent();
                            categoryToEvent.setCategory(
                                    categoryService.getCategoryById(categoryId));
                            categoryToEvent.setEvent(event);
                            categoryToEventService.createCategoryToEvent(categoryToEvent);
                        });
    }

    @Transactional
    public void updateEvent(EventRequestDto eventDto, Long id) {
        Event event = eventRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Event id not found!"));
        event.setName(eventDto.getName());
        event.setDescription(eventDto.getDescription());
        event.setPeselVerificationRequired(eventDto.isPeselVerificationRequired());
        event.setAgreementNeeded(eventDto.isAgreementNeeded());
        event.setImageUrl(eventDto.getImageUrl());

        updateEventCategories(event, eventDto);
        updateEventAddress(event, eventDto);
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

    private void updateEventAddress(Event event, EventRequestDto eventDto) {
        List<AddressToEvent> addressToEvent = event.getAddressToEvents();
        Address address = addressToEvent.get(0).getAddress();

        address.setDistrict(districtService.getDistrictById(eventDto.getDistrictId()));
        address.setStreet(eventDto.getStreet());
        address.setHomeNum(eventDto.getHomeNum());
        event.getAddressToEvents().forEach(ate -> ate.setAddress(address));
    }

    private void updateEventShifts(Event event, EventRequestDto eventDto) {
        List<Shift> newShifts = eventMapper.toShifts(eventDto.getShifts());

        event.getAddressToEvents()
                .forEach(ate ->ate.getShifts()
                                        .removeIf(s ->!newShifts.stream()
                                                                .map(Shift::getId)
                                                                .toList()
                                                                .contains(s.getId())));

        newShifts.forEach(ns -> {
                    if (ns.getId() == null) {
                        event.getAddressToEvents()
                                .get(0)
                                .getShifts()
                                .add(ns);
                    } else {
                        event.getAddressToEvents().get(0).getShifts().stream()
                                .filter(sh -> sh.getId().equals(ns.getId()))
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
        var thresholdDate = LocalDate.now().minusDays(5);

        List<Event> eventsTheyNeedYou = eventRepository.findEventsForTheyNeedYou(thresholdDate);
        if (!eventsTheyNeedYou.isEmpty()) {
            return eventsTheyNeedYou;
        } else {
            var pageable = PageRequest.of(0, 5);
            return eventRepository.findNearestEventsSortedByDate(LocalDate.now(), pageable);
        }
    }
}
