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
import pl.pjwstk.woloappapi.service.OrganisationService;
import pl.pjwstk.woloappapi.utils.EventMapper;
import pl.pjwstk.woloappapi.utils.OrganisationMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/organisations")
@Tag(name = "Organisations")
public class OrganisationController {
    private final OrganisationService organisationService;
    private final OrganisationMapper organisationMapper;
    private final EventMapper eventMapper;

    @Operation(
            summary = "Get all organisations",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(type = "array",implementation = OrganisationResponseDto.class)
                                    )
                            }
                    )
            }
    )
    @GetMapping()
    public ResponseEntity<List<OrganisationResponseDto>> getOrganisations() {
        List<Organisation> organisations = organisationService.getAllOrganisations();
        List<OrganisationResponseDto> organisationDtos =
                organisations.stream()
                        .map(organisationMapper::toOrganisationResponseDto)
                        .collect(Collectors.toList());
        return new ResponseEntity<>(organisationDtos, HttpStatus.OK);
    }

    @Operation(
            summary = "Get organisation by id",
            description = "Organisation must exist",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200" ,
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = OrganisationResponseDto.class)
                                    )
                            }
                    )
            },
            parameters = {
                    @Parameter(name = "id",
                            description = "Organisation id",
                            example = "1"
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<OrganisationResponseDto> getOrganisationById(@PathVariable Long id) {
        Organisation organisation = organisationService.getOrganisationById(id);
        OrganisationResponseDto organisationDto =
                organisationMapper.toOrganisationResponseDto(organisation);
        return new ResponseEntity<>(organisationDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Adding new organisation",
            description = "id = null",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201"
                    )
            },
            parameters = {
                    @Parameter(name = "organisationDto",
                            description = "Organisation object to create",
                            schema = @Schema(implementation = OrganisationRequestDto.class)
                    )
            }
    )
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addOrganisation(
            @Valid @RequestBody OrganisationRequestDto organisationDto) {
        organisationService.createOrganisation(organisationDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all events from the selected organisation",
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
    @GetMapping("/{id}/events")
    public ResponseEntity<List<EventResponseDto>> getEventsByOrganisation(@PathVariable Long id) {
        List<Event> events = organisationService.getEventsByOrganisation(id);
        List<EventResponseDto> eventDtos =
                events.stream()
                        .map(eventMapper::toEventResponseDto)
                        .collect(Collectors.toList());
        return new ResponseEntity<>(eventDtos, HttpStatus.OK);
    }

    @Operation(
            summary = "Edit organisation",
            description = "Organisation must exist",
            responses = {
                    @ApiResponse(
                            description = "No content",
                            responseCode = "204"
                    )
            },
            parameters = {
                    @Parameter(name = "organisation",
                            description = "Organisation object with changes",
                            schema = @Schema(implementation = OrganisationRequestDto.class)
                    )
            }
    )
    @PutMapping("/{id}/edit")
    public ResponseEntity<HttpStatus> editOrganisation(
            @Valid @RequestBody OrganisationRequestDto organisation, @PathVariable Long id) {
        organisationService.updateOrganisation(organisation, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/approve")
    public ResponseEntity<HttpStatus> approveOrganisation(@RequestParam(value = "id") Long organisationId){
        organisationService.approve(organisationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/disapprove")
    public ResponseEntity<HttpStatus> disapproveOrganisation(@RequestParam(value = "id") Long organisationId){
        organisationService.disapprove(organisationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
