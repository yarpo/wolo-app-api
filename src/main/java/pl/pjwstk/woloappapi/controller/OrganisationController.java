package pl.pjwstk.woloappapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjwstk.woloappapi.model.Event;
import pl.pjwstk.woloappapi.model.EventResponseDto;
import pl.pjwstk.woloappapi.model.Organisation;
import pl.pjwstk.woloappapi.model.OrganisationResponseDto;
import pl.pjwstk.woloappapi.service.OrganisationService;
import pl.pjwstk.woloappapi.utils.EventMapper;
import pl.pjwstk.woloappapi.utils.OrganisationMapper;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@AllArgsConstructor
@RequestMapping("/organisations")
public class OrganisationController {

    private final OrganisationService organisationService;
    private final OrganisationMapper organisationMapper;
    private final EventMapper eventMapper;

    @GetMapping()
    public ResponseEntity<List<OrganisationResponseDto>> getOrganisations(){
        List<Organisation> organisations = organisationService.getAllOrganisations();
        List<OrganisationResponseDto> organisationDtos = organisations.stream()
                .map(organisationMapper::toOrganisationResponseDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(organisationDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganisationResponseDto> getOrganisationById(@PathVariable Long id){
        Organisation organisation = organisationService.getOrganisationById(id);
        OrganisationResponseDto organisationDto = organisationMapper
                .toOrganisationResponseDto(organisation);
        return new ResponseEntity<>(organisationDto, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addOrganisation(@Valid @RequestBody Organisation Organisation){
        organisationService.createOrganisation(Organisation);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteOrganisation(@PathVariable Long id){
        organisationService.deleteOrganisation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<List<EventResponseDto>> getEventsByOrganisation(@PathVariable Long id) {
        List<Event> events = organisationService.getEventsByOrganisation(id);
        List<EventResponseDto> eventDtos = events.stream()
                .map(eventMapper::toEventResponseDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(eventDtos, HttpStatus.OK);
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<HttpStatus> editOrganisation(@Valid @RequestBody Organisation organisation,
                                                   @PathVariable Long id) {
        organisationService.updateOrganisation(organisation, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}