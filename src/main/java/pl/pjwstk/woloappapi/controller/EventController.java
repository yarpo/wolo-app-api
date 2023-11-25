package pl.pjwstk.woloappapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjwstk.woloappapi.model.*;
import pl.pjwstk.woloappapi.repository.CategoryRepository;
import pl.pjwstk.woloappapi.service.*;
import pl.pjwstk.woloappapi.utils.EventMapper;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;
    private final DistrictService districtService;
    private final OrganisationService organisationService;
    private final AddressService addressService;
    private final AddressToEventSevice addressToEventService;
    private final CategoryService categoryService;
    private final ShiftService shiftService;
    private final CategoryRepository categoryRepository;


    @GetMapping()
    public ResponseEntity<List<EventResponseDto>> getEvents() {
        List<Event> events = eventService.getAllEvents();
        List<EventResponseDto> eventDtos = events.stream()
                .map(eventMapper::toEventResponseDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(eventDtos, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Event>> filterEvents(@RequestParam(value = "localization", required = false) String[] localizations,
                                                    @RequestParam(value = "startDate", required = false) LocalDate startDate,
                                                    @RequestParam(value = "endDate", required = false) LocalDate endDate,
                                                    @RequestParam(value = "category", required = false) Long category,
                                                    @RequestParam(value = "organizer", required = false) Long organizer,
                                                    @RequestParam(value = "ageRestriction", required = false) Integer ageRestriction,

                                                    @RequestParam(value = "verification", required = false) boolean isPeselVerificationRequired){


        List<Event> filteredEvents = eventService.filterEvents(localizations, startDate, endDate, category, organizer,
                ageRestriction, isPeselVerificationRequired);
        return new ResponseEntity<>(filteredEvents, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id){
        Event event = eventService.getEventById(id);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addEvent(@Valid @RequestBody DtoRequestEvent dtoEvent){
        Address address = eventMapper.INSTANCE.toAddress(dtoEvent);
        District district = districtService.getDistrictById(dtoEvent.getDistrictId());
        address.setDistrict(district);

        List<Shift> shifts = eventMapper.INSTANCE.toShifts(dtoEvent.getShifts());

        Event event = eventMapper.INSTANCE.toEvent(dtoEvent);
        Organisation organisation = organisationService.getOrganisationById(dtoEvent.getOrganisationId());

        AddressToEvent addressToEvent = new AddressToEvent();
        addressToEvent.setEvent(event);
        addressToEvent.setAddress(address);

        shifts.forEach(shift -> {
            shift.setAddressToEvent(addressToEvent);
            addressToEvent.getShifts().add(shift);
            shiftService.createShift(shift);
        });


        List<CategoryToEvent> categoryToEvents = dtoEvent.getCategories().stream()
                .map(categoryId -> {
                    Category category = categoryService.getCategoryById(categoryId);
                    CategoryToEvent categoryToEvent = new CategoryToEvent();
                    categoryToEvent.setCategory(category);
                    categoryToEvent.setEvent(event);
                    return categoryToEvent;
                })
                .collect(Collectors.toList());

        event.setCategories(categoryToEvents);

        event.setOrganisation(organisation);
        event.getAddressToEvents().add(addressToEvent);

        address.getAddressToEvents().add(addressToEvent);

        addressService.createAddress(address);
        eventService.createEvent(event);
        addressToEventService.createAddressToEvent(addressToEvent);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteEvent(@PathVariable Long id){
        eventService.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<HttpStatus> editDistrict(@Valid @RequestBody Event event,
                                                   @PathVariable Long id) {
        eventService.updateEvent(event, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

