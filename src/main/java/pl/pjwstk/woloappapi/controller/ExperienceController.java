package pl.pjwstk.woloappapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.pjwstk.woloappapi.model.Experience;
import pl.pjwstk.woloappapi.repository.ExperienceRepository;

@RestController
public class ExperienceController{

    private ExperienceRepository ExperienceRepository;

    @Autowired
    public ExperienceController(ExperienceRepository ExperienceRepository) {
        this.ExperienceRepository = ExperienceRepository;
    }

    @GetMapping("/Experience/all")
    Iterable<Experience> all() {
        return ExperienceRepository.findAll();
    }

    @GetMapping("/Experience/{id}")
    Experience ExperienceById(@PathVariable Long id) {
        return ExperienceRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND));
    }

    @PostMapping("/Experience/save")
    Experience save(@RequestBody Experience Experience) {
        return ExperienceRepository.save(Experience);
    }

}