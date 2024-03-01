package pl.pjwstk.woloappapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjwstk.woloappapi.model.*;
import pl.pjwstk.woloappapi.service.EventService;
import pl.pjwstk.woloappapi.service.UserService;
import pl.pjwstk.woloappapi.utils.EventMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/events")
@Tag(name = "Events")
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;
    private final UserService userService;

    @Operation(
            summary = "Get all events",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(type = "array",implementation = EventResponseDto.class)
                                    )
                            }
                    )
            }
    )
    @GetMapping("")
    public ResponseEntity<List<EventResponseDto>> getEvents() {
        List<EventResponseDto> eventDtos = eventService.getAllEvents()
                .stream()
                .map(eventMapper::toEventResponseDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(eventDtos, HttpStatus.OK);
    }

    @Operation(
            summary = "User joins the event",
            description = "User and event must exist",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    )
            },
            parameters = {
                    @Parameter(name = "user",description = "User id who wants to join the event"),
                    @Parameter(name = "shift",description = "Shift id that the user wants to join"),
            }
    )
    @PostMapping("/join")
    public ResponseEntity<HttpStatus> joinEvent(
            @RequestParam(value = "user") Long userId,
            @RequestParam(value = "shift") Long shiftId){
        userService.joinEvent(userId, shiftId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Filter events",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(type = "array",implementation = EventResponseDto.class)
                                    )
                            }
                    )
            },
            parameters = {
                    @Parameter(
                            name = "localization",
                            description = "Array of District names",
                            schema = @Schema(type = "array", implementation = String.class)
                    ),
                    @Parameter(
                            name = "startDate",
                            description = "The beginning of the time interval within which to search for an event",
                            example = "2022-01-01",
                            schema = @Schema(type = "string", format = "date")
                    ),
                    @Parameter(
                            name = "endDate",
                            description = "The end of the time interval within which to search for an event",
                            example = "2022-12-31",
                            schema = @Schema(type = "string", format = "date")
                    ),
                    @Parameter(
                            name = "categories",
                            description = "Array of category ids",
                            schema = @Schema(type = "array", implementation = Long.class)
                    ),
                    @Parameter(
                            name = "organizer",
                            description = "Id of the organization organizing the event",
                            schema = @Schema(type = "long")
                    ),
                    @Parameter(
                            name = "ageRestriction",
                            description = "From what age are participants allowed",
                            schema = @Schema(type = "integer"),
                            example = "18"
                    ),
                    @Parameter(
                            name = "isPeselVerificationRequired",
                            description = "Whether pesel verification is required",
                            schema = @Schema(type = "boolean")
                    ),
                    @Parameter(
                            name = "showWithAvailableCapacity",
                            description = "Show only those events that are still looking for volunteers",
                            schema = @Schema(type = "boolean")
                    )
            }
    )
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

    @Operation(
            summary = "Get event by id",
            description = "Event must exist",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200" ,
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = EventResponseDetailsDto.class)
                                    )
                            }

                    )
            },
            parameters = {
                    @Parameter(name = "id",
                            description = "Event id",
                            example = "1"
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDetailsDto> getEventById(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        EventResponseDetailsDto eventDto = eventMapper.toEventResponseDetailsDto(event);
        return new ResponseEntity<>(eventDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Adding new event",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201"
                    )
            },
            parameters = {
                    @Parameter(name = "dtoEvent",
                            description = "Category object to create",
                            schema = @Schema(implementation = EventRequestDto.class)
                    )
            }
    )
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addEvent(@Valid @RequestBody EventRequestDto dtoEvent) {
            eventService.createEvent(dtoEvent);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete event",
            description = "Only upcoming events can be deleted",
            responses = {
                    @ApiResponse(
                            description = "No content",
                            responseCode = "204"
                    )
            },
            parameters = {
                    @Parameter(name = "id",
                            description = "Event id",
                            example = "1"
                    )
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Edit event",
            description = "Event must exist, you can change only upcoming events",
            responses = {
                    @ApiResponse(
                            description = "No content",
                            responseCode = "204"
                    )
            },
            parameters = {
                    @Parameter(name = "eventRequestDto",
                            description = "Event object with changes",
                            schema = @Schema(implementation = EventRequestDto.class)
                    ),
                    @Parameter(name = "id",
                            description = "Event id to make changes"
                    ),
            }
    )
    @PutMapping("/{id}/edit")
    public ResponseEntity<HttpStatus> editEvent(
            @Valid @RequestBody EventRequestDto eventRequestDto, @PathVariable Long id) {
        eventService.updateEvent(eventRequestDto, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Get only upcoming events",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(type = "array",implementation = EventResponseDto.class)
                                    )
                            }
                    )
            }
    )
    @GetMapping("/upcoming")
    public ResponseEntity<List<EventResponseDto>>getUpcomingEvents(){
        List<Event> events = eventService.getUpcomingEvents();
        List<EventResponseDto> respons = events.stream()
                .map(eventMapper::toEventResponseDto).toList();
        return new ResponseEntity<>(respons, HttpStatus.OK);
    }
}