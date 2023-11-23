package pl.pjwstk.woloappapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjwstk.woloappapi.model.Event;
import pl.pjwstk.woloappapi.model.Organisation;
import pl.pjwstk.woloappapi.service.OrganisationService;

import javax.validation.Valid;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/organisations")
public class OrganisationController {

    private final OrganisationService organisationService;

    @GetMapping()
    public ResponseEntity<List<Organisation>> getOrganisations(){
        List<Organisation> Organisations = organisationService.getAllOrganisations();
        return new ResponseEntity<>(Organisations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Organisation> getOrganisationById(@PathVariable Long id){
        return new ResponseEntity<>(organisationService.getOrganisationById(id), HttpStatus.OK);
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
    public ResponseEntity<List<Event>> getEventsByOrganizer(@PathVariable Long id) {
        List<Event> eventsByOrganizer = organisationService.getEventsByOrganizer(id);
        return new ResponseEntity<>(eventsByOrganizer, HttpStatus.OK);
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<HttpStatus> editOrganisation(@Valid @RequestBody Organisation organisation,
                                                   @PathVariable Long id) {
        organisationService.updateOrganisation(organisation, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}