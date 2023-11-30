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
                new IllegalArgumentException("event with ID " + id + " does not exist"));

        if (!Objects.equals(event.getName(), eventDto.getName())) {
            event.setName(eventDto.getName());
        }

        if (!Objects.equals(event.getDescription(), eventDto.getDescription())) {
            event.setDescription(eventDto.getDescription());
        }

        if (!Objects.equals(event.isPeselVerificationRequired(), eventDto.isPeselVerificationRequired())) {
            event.setPeselVerificationRequired(eventDto.isPeselVerificationRequired());
        }

        if (!Objects.equals(event.isAgreementNeeded(), eventDto.isAgreementNeeded())) {
            event.setAgreementNeeded(eventDto.isAgreementNeeded());
        }

        if (!Objects.equals(event.getOrganisation().getId(), eventDto.getOrganisationId())) {
            Organisation organisation = organisationService.getOrganisationById(eventDto.getOrganisationId());
            event.setOrganisation(organisation);
        }

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

        List<AddressToEvent> addressToEvent = event.getAddressToEvents();
        Address address = addressToEvent.get(0).getAddress();

        if (!Objects.equals(address.getDistrict().getId(), eventDto.getDistrictId())) {
            District district = districtService.getDistrictById(eventDto.getDistrictId());
            address.setDistrict(district);
        }

        if (!Objects.equals(address.getStreet(), eventDto.getStreet())) {
            address.setStreet(eventDto.getStreet());
        }

        if (!Objects.equals(address.getHomeNum(), eventDto.getHomeNum())) {
            address.setHomeNum(eventDto.getHomeNum());
        }

        event.getAddressToEvents().forEach(ate ->ate.setAddress(address));

        List<Shift> newShifts = eventMapper.INSTANCE.toShifts(eventDto.getShifts());
        event.getAddressToEvents().forEach(ate ->
                ate.getShifts().removeIf(shift -> !newShifts.stream()
                        .map(Shift::getId).toList().contains(shift.getId())));

        newShifts.forEach(newShift ->{
            if(newShift.getId() == null){
                event.getAddressToEvents().get(0).getShifts().add(newShift);
            }else{
                Shift shift = event.getAddressToEvents().get(0).getShifts().stream()
                        .filter(sh -> sh.getId().equals(newShift.getId()))
                        .findFirst().get();
                if(!Objects.equals(shift.getDate(), newShift.getDate())){
                    shift.setDate(newShift.getDate());
                }

                if(!Objects.equals(shift.getStartTime(), newShift.getStartTime())){
                    shift.setStartTime(newShift.getStartTime());
                }

                if(!Objects.equals(shift.getEndTime(), newShift.getEndTime())){
                    shift.setEndTime(newShift.getEndTime());
                }

                if (!Objects.equals(shift.getCapacity(), newShift.getCapacity())){
                    shift.setCapacity(newShift.getCapacity());
                }

                if (!Objects.equals(shift.isLeaderRequired(), newShift.isLeaderRequired())){
                    shift.setLeaderRequired(newShift.isLeaderRequired());
                }

                if(!Objects.equals(shift.getRequiredMinAge(), newShift.getRequiredMinAge())){
                    shift.setRequiredMinAge(newShift.getRequiredMinAge());
                }
            }
        });


        eventRepository.save(event);

    }

    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new IllegalArgumentException("Event with ID " + id + " does not exist");
        }
        eventRepository.deleteById(id);
    }


    public List<Event> filterEvents(String[] localizations, LocalDate startDate, LocalDate endDate,
                                    Long category, Long organizer, Integer ageRestriction,
                                    boolean isPeselVerificationRequired) {
        return eventRepository.findAllByFilter(localizations, startDate, endDate,
                category, organizer, ageRestriction, isPeselVerificationRequired);
    }

}
