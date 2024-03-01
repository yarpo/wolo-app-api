package pl.pjwstk.woloappapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjwstk.woloappapi.model.Event;
import pl.pjwstk.woloappapi.model.EventRequestDto;
import pl.pjwstk.woloappapi.model.EventResponseDetailsDto;
import pl.pjwstk.woloappapi.model.EventResponseDto;
import pl.pjwstk.woloappapi.service.EventService;
import pl.pjwstk.woloappapi.service.UserService;
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
    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<List<EventResponseDto>> getEvents() {
        List<EventResponseDto> eventDtos = eventService.getAllEvents()
                .stream()
                .map(eventMapper::toEventResponseDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(eventDtos, HttpStatus.OK);
    }
    @PostMapping("/join")
    public ResponseEntity<HttpStatus> joinEvent(
            @RequestParam(value = "user") Long userId,
            @RequestParam(value = "shift") Long shiftId){
        userService.joinEvent(userId, shiftId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/refuse")
    public ResponseEntity<HttpStatus> refuseParticipateInEvent(
            @RequestParam(value = "user") Long userId,
            @RequestParam(value = "shift") Long shiftId){
        userService.refuse(userId, shiftId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<EventResponseDto>> filterEvents(
            @RequestParam(value = "localization", required = false) String[] localizations,
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate,
            @RequestParam(value = "category", required = false) Long[] categories,
            @RequestParam(value = "organizer", required = false) Long organizer,
            @RequestParam(value = "ageRestriction", required = false) Integer ageRestriction,
            @RequestParam(value = "verification", required = false)
                    Boolean isPeselVerificationRequired,
            @RequestParam(value = "showAvailable", required = false)
                    Boolean showWithAvailableCapacity) {

        List<Event> filteredEvents =
                eventService.filterEvents(
                        localizations,
                        startDate,
                        endDate,
                        categories,
                        organizer,
                        ageRestriction,
                        isPeselVerificationRequired,
                        showWithAvailableCapacity);
        List<EventResponseDto> eventDtos =
                filteredEvents
                        .stream()
                        .map(eventMapper::toEventResponseDto)
                        .collect(Collectors.toList());
        return new ResponseEntity<>(eventDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDetailsDto> getEventById(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        EventResponseDetailsDto eventDto = eventMapper.toEventResponseDetailsDto(event);
        return new ResponseEntity<>(eventDto, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addEvent(@Valid @RequestBody EventRequestDto dtoEvent) {
            eventService.createEvent(dtoEvent);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<HttpStatus> editEvent(
            @Valid @RequestBody EventRequestDto eventRequestDto, @PathVariable Long id) {
        eventService.updateEvent(eventRequestDto, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<EventResponseDto>>getUpcomingEvents(){
        List<Event> events = eventService.getUpcomingEvents();
        List<EventResponseDto> respons = events
                .stream()
                .map(eventMapper::toEventResponseDto)
                .toList();
        return new ResponseEntity<>(respons, HttpStatus.OK);
    }
}