package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pjwstk.woloappapi.model.*;
import pl.pjwstk.woloappapi.repository.EventRepository;
import pl.pjwstk.woloappapi.utils.EventMapper;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    public void updateEvent(EventRequestEditDto eventRequestEditDto, Long id) {

        if (!eventRepository.existsById(id)) {
            throw new IllegalArgumentException("Event with ID " + id + " does not exist");
        }

        Event event = eventRepository.getReferenceById(id);
        //Address address = addressService.getAddressById(event.getAddressToEvents().g);

        //Działa
        if(eventRequestEditDto.getName() != null){
            event.setName(eventRequestEditDto.getName());
        }

        //Działa
        if(eventRequestEditDto.getDescription() != null){
            event.setDescription(eventRequestEditDto.getDescription());
        }

        //Działa
        event.setPeselVerificationRequired(eventRequestEditDto.isPeselVerificationRequired());

        //Działa
        event.setAgreementNeeded(eventRequestEditDto.isAgreementNeeded());

        //Działa
        if(eventRequestEditDto.getOrganisationId() != null){
            Organisation organisation = organisationService.getOrganisationById(eventRequestEditDto.getOrganisationId());
            event.setOrganisation(organisation);
        }

        //Dział z Kategoriami-do sprawdzenia/rozpatrzenia
        if(eventRequestEditDto.getCategories() != null){
            List<CategoryToEvent> categoryToEvents = eventRequestEditDto.getCategories().stream()
                    .map(categoryId -> {
                        Category category = categoryService.getCategoryById(categoryId);
                        CategoryToEvent categoryToEvent = new CategoryToEvent();
                        categoryToEvent.setCategory(category);
                        categoryToEvent.setEvent(event);
                        return categoryToEvent;
                    })
                    .collect(Collectors.toList());

            event.setCategories(categoryToEvents);
        }

        //Dział z adresem-do sprawdzenia/rozpatrzenia
        List<AddressToEvent> addressToEvent = event.getAddressToEvents();
        Address address = addressToEvent.get(0).getAddress();

        //Działa
        if(eventRequestEditDto.getDistrictId() != null){
            District district = districtService.getDistrictById(eventRequestEditDto.getDistrictId());
            address.setDistrict(district);
        }
        //Działa
        if(eventRequestEditDto.getStreet() != null){
            address.setStreet(eventRequestEditDto.getStreet());
        }
        //Działa
        if(eventRequestEditDto.getHomeNum() != null){
            address.setHomeNum(eventRequestEditDto.getHomeNum());
        }
        addressToEvent.get(0).setAddress(address);

        //Dział z shiftami-do sprawdzenia/rozpatrzenia
        if(eventRequestEditDto.getShifts() != null){
            List<Shift> shifts = eventMapper.INSTANCE.toShifts(eventRequestEditDto.getShifts());
            shifts.forEach(shift -> {
                shift.setAddressToEvent(addressToEvent.get(0));
                addressToEvent.get(0).getShifts().add(shift);
                shiftService.updateShift(shift);
            });
        }

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
