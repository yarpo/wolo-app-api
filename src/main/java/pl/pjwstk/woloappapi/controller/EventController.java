package pl.pjwstk.woloappapi.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import pl.pjwstk.woloappapi.model.*;
import pl.pjwstk.woloappapi.model.entities.Event;
import pl.pjwstk.woloappapi.model.entities.User;
import pl.pjwstk.woloappapi.service.EventService;
import pl.pjwstk.woloappapi.service.PDFGenerationService;
import pl.pjwstk.woloappapi.service.UserService;
import pl.pjwstk.woloappapi.utils.EventMapper;
import pl.pjwstk.woloappapi.utils.UserMapper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_PDF;

@RestController
@AllArgsConstructor
@RequestMapping("/events")
@Tag(name = "Event")
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final PDFGenerationService pdfGenerationService;


    @GetMapping("")
    public ResponseEntity<List<EventResponseDto>> getEvents() {
        List<EventResponseDto> eventDtos = eventService.getAllEvents()
                .stream()
                .map(eventMapper::toEventResponseDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(eventDtos, HttpStatus.OK);
    }

    @GetMapping("/need")
    public ResponseEntity<List<EventResponseDto>> getTheyNeedYouList() {
        List<EventResponseDto> eventDtos = eventService.getTheyNeedYouList()
                .stream()
                .map(eventMapper::toEventResponseDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(eventDtos, HttpStatus.OK);
    }

    @PostMapping("/join")
    public ResponseEntity<HttpStatus> joinEvent(@RequestParam(value = "shift") Long shiftId){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = userService.getCurrentUser(authentication).getId();
        userService.joinEvent(userId, shiftId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/refuse")
    public ResponseEntity<HttpStatus> refuseParticipateInEvent(@RequestParam(value = "shift") Long shiftId){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = userService.getCurrentUser(authentication).getId();
        userService.refuse(userId, shiftId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserShortResponse>> getUsersByShift(
            @RequestParam(value = "shift", required = false) Long shift){
        List<User> users = userService.getUsersByShift(shift);
        List<UserShortResponse> userDetails = users.stream()
                .map(userMapper::toUserShortRespons)
                .toList();
        return new ResponseEntity<>(userDetails, HttpStatus.OK);

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
    public ResponseEntity<HttpStatus> addEvent(@Valid @RequestBody EventRequestDto dtoEvent,
                                               @RequestParam String language) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var organisationId = userService.getCurrentUser(authentication).getOrganisation().getId();
        if(Objects.equals(organisationId, dtoEvent.getOrganisationId())) {
            var translationDto = eventMapper.toEventTranslationDto(dtoEvent, language);
            var localClient = WebClient.create("http://host.docker.internal:5000/");
            localClient.post()
                    .uri("/event-create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(translationDto)
                    .retrieve()
                    .bodyToMono(EventTranslationResponse.class)
                    .subscribe(translated -> eventService.createEvent(translated, dtoEvent));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }else{
            throw new IllegalArgumentException("You can create events only for your organisation");
        }
    }

    @PostMapping("/admin/add")
    public ResponseEntity<HttpStatus> addEventByAdmin(
            @Valid @RequestBody EventRequestDto dtoEvent,
            @RequestParam String language) {
        var translationDto = eventMapper.toEventTranslationDto(dtoEvent, language);
        var localClient = WebClient.create("http://host.docker.internal:5000/");
        localClient.post()
                .uri("/event-create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(translationDto)
                .retrieve()
                .bodyToMono(EventTranslationResponse.class)
                .subscribe(translated -> eventService.createEvent(translated, dtoEvent));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteEvent(@PathVariable Long id) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var organisationId = userService.getCurrentUser(authentication).getOrganisation().getId();
        var event = eventService.getEventById(id);
        if(Objects.equals(organisationId, event.getOrganisation().getId())) {
            eventService.deleteEvent(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            throw new IllegalArgumentException("You can delete events only for your organisation");
        }
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<HttpStatus> deleteEventByAdmin(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/admin/edit/{id}")
    public ResponseEntity<HttpStatus> editEventByAdmin(
            @Valid @RequestBody EventRequestDto eventRequestDto,
            @PathVariable Long id,
            @RequestParam String language) {
        return sendRequestToTranslator(eventRequestDto, id, language);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<HttpStatus> editEvent(
            @Valid @RequestBody EventRequestDto eventRequestDto,
            @PathVariable Long id,
            @RequestParam String language) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var organisationId = userService.getCurrentUser(authentication).getOrganisation().getId();
        var event = eventService.getEventById(id);
        if(Objects.equals(organisationId, event.getOrganisation().getId())) {
            return sendRequestToTranslator(eventRequestDto, id, language);
        }else{
            throw new IllegalArgumentException("You can edit events only for your organisation");
        }
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

    @GetMapping("/users/pdf")
    public ResponseEntity<ByteArrayResource> getListOfUsersAsPDF(
            @RequestParam(value = "id") Long eventId) throws IOException {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var organisationId = userService.getCurrentUser(authentication).getOrganisation().getId();
        var event = eventService.getEventById(eventId);
        if(Objects.equals(organisationId, event.getOrganisation().getId())) {
            var shifts = event.getShifts();
            var pdfBytes = pdfGenerationService.generatePDFForAllShifts(shifts);

            var headers = new HttpHeaders();
            headers.setContentType(APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "users_on_shifts.pdf");

            ByteArrayResource resource = new ByteArrayResource(pdfBytes);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        }else {
            throw new IllegalArgumentException("You can get list of volunteers sign in for event only for your organisation");
        }
    }

    private ResponseEntity<HttpStatus> sendRequestToTranslator(@RequestBody @Valid EventRequestDto eventRequestDto, @PathVariable Long id, @RequestParam String language) {
        var translationDto = eventMapper.toEventTranslationDto(eventRequestDto, language);
        var localClient = WebClient.create("http://host.docker.internal:5000/");
        localClient.post()
                .uri("/event-create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(translationDto)
                .retrieve()
                .bodyToMono(EventTranslationResponse.class)
                .subscribe(translated -> eventService.updateEvent(eventRequestDto, id, translated));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}