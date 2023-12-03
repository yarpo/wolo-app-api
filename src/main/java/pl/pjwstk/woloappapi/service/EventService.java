package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pjwstk.woloappapi.model.*;
import pl.pjwstk.woloappapi.repository.EventRepository;
import pl.pjwstk.woloappapi.utils.EventMapper;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

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
    private final CategoryToEventService  categoryToEventService;

     public List<Event> getAllEvents() {
         return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event id not found!"));
    }

    public void createEvent(EventRequestDto dtoEvent){
        Address address = eventMapper.INSTANCE.toAddress(dtoEvent);
        District district = districtService.getDistrictById(dtoEvent.getDistrictId());
        address.setDistrict(district);

        Event event = eventMapper.INSTANCE.toEvent(dtoEvent);
        Organisation organisation = organisationService.getOrganisationById(dtoEvent.getOrganisationId());
        event.setOrganisation(organisation);

        AddressToEvent addressToEvent = new AddressToEvent();
        addressToEvent.setEvent(event);
        addressToEvent.setAddress(address);
        addressToEventService.createAddressToEvent(addressToEvent);

        List<Shift> shifts = eventMapper.INSTANCE.toShifts(dtoEvent.getShifts());
        shifts.forEach(shift -> {
            shift.setAddressToEvent(addressToEvent);
            shiftService.createShift(shift);
        });

        dtoEvent.getCategories().forEach(categoryId -> {
            CategoryToEvent categoryToEvent = new CategoryToEvent();
            categoryToEvent.setCategory(categoryService.getCategoryById(categoryId));
            categoryToEvent.setEvent(event);
            categoryToEventService.createCategoryToEvent(categoryToEvent);
        });
    }

    public void updateEvent(EventRequestDto eventDto, Long id) {
        Event event = eventRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Event with ID " + id + " does not exist"));

        updateFieldIfDifferent(event::getName, event::setName, eventDto.getName());
        updateFieldIfDifferent(event::getDescription, event::setDescription, eventDto.getDescription());
        updateFieldIfDifferent(event::isPeselVerificationRequired, event::setPeselVerificationRequired, eventDto.isPeselVerificationRequired());
        updateFieldIfDifferent(event::isAgreementNeeded, event::setAgreementNeeded, eventDto.isAgreementNeeded());

        Organisation organisation = organisationService.getOrganisationById(eventDto.getOrganisationId());
        updateFieldIfDifferent(() -> event.getOrganisation().getId(), oId -> event.setOrganisation(organisation), eventDto.getOrganisationId());

        updateEventCategories(event, eventDto);
        updateEventAddress(event, eventDto);
        updateEventShifts(event, eventDto);

        eventRepository.save(event);
    }

    private void updateEventCategories(Event event, EventRequestDto eventDto) {
        event.getCategories().removeIf(categoryToEvent ->
                !eventDto.getCategories().contains(categoryToEvent.getCategory().getId()));

        eventDto.getCategories().stream()
                .filter(categoryId -> event.getCategories().stream()
                        .noneMatch(categoryToEvent -> categoryId.equals(categoryToEvent.getCategory().getId())))
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

        updateFieldIfDifferent(address.getDistrict()::getId,
                districtId -> address.setDistrict( districtService.getDistrictById(districtId)),
                eventDto.getDistrictId());
        updateFieldIfDifferent(address::getStreet, address::setStreet, eventDto.getStreet());
        updateFieldIfDifferent(address::getHomeNum, address::setHomeNum, eventDto.getHomeNum());
        updateFieldIfDifferent(address::getAddressDescription,
                address::setAddressDescription,
                eventDto.getAddressDescription());

        event.getAddressToEvents().forEach(ate -> ate.setAddress(address));
    }

    private void updateEventShifts(Event event, EventRequestDto eventDto) {
        List<Shift> newShifts = eventMapper.INSTANCE.toShifts(eventDto.getShifts());

        event.getAddressToEvents().forEach(ate ->
                ate.getShifts().removeIf(shift -> !newShifts.stream()
                        .map(Shift::getId)
                        .toList()
                        .contains(shift.getId())));

        newShifts.forEach(newShift -> {
            if (newShift.getId() == null) {
                event.getAddressToEvents().get(0).getShifts().add(newShift); //adding new shifts
            } else {
                event.getAddressToEvents().get(0).getShifts().stream()
                        .filter(sh -> sh.getId().equals(newShift.getId()))
                        .findFirst()
                        .ifPresent(existingShift -> updateShiftFields(existingShift, newShift));
            }
        });
    }

    private void updateShiftFields(Shift shift, Shift newShift) {
        updateFieldIfDifferent(shift::getDate, shift::setDate, newShift.getDate());
        updateFieldIfDifferent(shift::getStartTime, shift::setStartTime, newShift.getStartTime());
        updateFieldIfDifferent(shift::getEndTime, shift::setEndTime, newShift.getEndTime());
        updateFieldIfDifferent(shift::getCapacity, shift::setCapacity, newShift.getCapacity());
        updateFieldIfDifferent(shift::isLeaderRequired, shift::setLeaderRequired, newShift.isLeaderRequired());
        updateFieldIfDifferent(shift::getRequiredMinAge, shift::setRequiredMinAge, newShift.getRequiredMinAge());
    }


    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }


    public List<Event> filterEvents(String[] localizations, LocalDate startDate, LocalDate endDate,
                                    Long[] categories, Long organizer, Integer ageRestriction,
                                    Boolean isPeselVerificationRequired, Boolean showWithAvailableCapacity) {
        return eventRepository.findAllByFilter(localizations, startDate, endDate,
                categories, organizer, ageRestriction, isPeselVerificationRequired, showWithAvailableCapacity);
    }

    private <T> void updateFieldIfDifferent(Supplier<T> currentSupplier,
                                            Consumer<T> updateConsumer,
                                            T newValue) {
        if (!Objects.equals(currentSupplier.get(), newValue)) {
            updateConsumer.accept(newValue);
        }
    }

}
