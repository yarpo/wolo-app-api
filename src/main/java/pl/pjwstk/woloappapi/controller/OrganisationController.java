package pl.pjwstk.woloappapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.pjwstk.woloappapi.model.Organisation;
import pl.pjwstk.woloappapi.repository.OrganisationRepository;

@RestController
@AllArgsConstructor
@RequestMapping("/organisation")
public class OrganisationController {

    private final OrganisationRepository OrganisationRepository;

    @GetMapping("/all")
    Iterable<Organisation> all() {
        return OrganisationRepository.findAll();
    }

    @GetMapping("/{id}")
    Organisation OrganisationById(@PathVariable Long id) {
        return OrganisationRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    Organisation save(@RequestBody Organisation Organisation) {
        return OrganisationRepository.save(Organisation);
    }

}
