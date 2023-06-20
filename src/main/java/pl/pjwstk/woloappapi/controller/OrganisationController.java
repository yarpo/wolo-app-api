package pl.pjwstk.woloappapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.pjwstk.woloappapi.model.Organisation;
import pl.pjwstk.woloappapi.repository.OrganisationRepository;

@RestController
public class OrganisationController {

    private OrganisationRepository OrganisationRepository;

    @Autowired
    public OrganisationController(OrganisationRepository OrganisationRepository) {
        this.OrganisationRepository = OrganisationRepository;
    }

    @GetMapping("/Organisation/all")
    Iterable<Organisation> all() {
        return OrganisationRepository.findAll();
    }

    @GetMapping("/Organisation/{id}")
    Organisation OrganisationById(@PathVariable Long id) {
        return OrganisationRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND));
    }

    @PostMapping("/Organisation/save")
    Organisation save(@RequestBody Organisation Organisation) {
        return OrganisationRepository.save(Organisation);
    }

}