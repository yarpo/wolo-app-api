package pl.pjwstk.woloappapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.pjwstk.woloappapi.model.Shift;
import pl.pjwstk.woloappapi.repository.ShiftRepository;

@RestController
public class ShiftController {

    private ShiftRepository TermRepository;

    @Autowired
    public ShiftController(ShiftRepository TermRepository) {
        this.TermRepository = TermRepository;
    }

    @GetMapping("/Term/all")
    Iterable<Shift> all() {
        return TermRepository.findAll();
    }

    @GetMapping("/Term/{id}")
    Shift TermById(@PathVariable Long id) {
        return TermRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND));
    }

    @PostMapping("/Term/save")
    Shift save(@RequestBody Shift Term) {
        return TermRepository.save(Term);
    }

}