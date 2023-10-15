package pl.pjwstk.woloappapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjwstk.woloappapi.model.Organisation;
import pl.pjwstk.woloappapi.service.OrganisationService;

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
    public ResponseEntity<HttpStatus> addOrganisation(@RequestBody Organisation Organisation){
        organisationService.createOrganisation(Organisation);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteOrganisation(@PathVariable Long id){
        organisationService.deleteOrganisation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}