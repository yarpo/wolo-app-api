package pl.pjwstk.woloappapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import pl.pjwstk.woloappapi.model.*;
import pl.pjwstk.woloappapi.service.EventService;
import pl.pjwstk.woloappapi.service.UserService;
import pl.pjwstk.woloappapi.utils.EventMapper;
import pl.pjwstk.woloappapi.utils.Translator;

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
    private final Translator translator;
    private final UserService userService;

    private final WebClient webClient;

    @GetMapping("")
    public ResponseEntity<List<EventResponseDto>> getEvents(
            @RequestParam(value = "language") String language) {
        List<EventResponseDto> eventDtos = eventService.getAllEvents()
                .stream()
                .map(e -> eventMapper.toEventResponseDto(e, translator.translate(language, e)))
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
                    Boolean showWithAvailableCapacity,
            @RequestParam(value = "language") String language) {

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
                        .map(e -> eventMapper.toEventResponseDto(e, translator.translate(language, e)))
                        .collect(Collectors.toList());
        return new ResponseEntity<>(eventDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDetailsDto> getEventById(@PathVariable Long id,
                                                                @RequestParam(value = "language") String language) {
        Event event = eventService.getEventById(id);
        EventResponseDetailsDto eventDto = eventMapper.toEventResponseDetailsDto(event, translator.translate(language, event));
        return new ResponseEntity<>(eventDto, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addEvent(@Valid @RequestBody EventRequestDto dtoEvent) {
        EventTranslationRequestDto translationDto = eventMapper.toEventTranslationDto(dtoEvent);
        var translatedObject =webClient.post()
                .uri("/translate")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(translationDto)
                .retrieve()
                .bodyToMono(EventTranslationResponsDto.class)
                .block();
        if (translatedObject != null) {
            eventService.createEvent(translatedObject, dtoEvent);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/edit")
    public ResponseEntity<HttpStatus> editEvent(
            @Valid @RequestBody EventRequestDto eventRequestDto, @PathVariable Long id) {

        eventService.updateEvent(eventRequestDto, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<EventAIRequest>>getUpcomingEvents(){
        List<Event> events = eventService.getUpcomingEvents();
        List<EventAIRequest> aiRequests = events.stream()
                .map(eventMapper::toEventAIRequest).toList();
        return new ResponseEntity<>(aiRequests, HttpStatus.OK);
    }

}