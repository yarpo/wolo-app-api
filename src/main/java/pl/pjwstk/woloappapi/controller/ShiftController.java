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
import pl.pjwstk.woloappapi.model.Shift;
import pl.pjwstk.woloappapi.repository.ShiftRepository;

@RestController
@AllArgsConstructor
@RequestMapping("/term")

public class ShiftController {

    private final ShiftRepository TermRepository;

    @GetMapping("/all")
    Iterable<Shift> all() {
        return TermRepository.findAll();
    }

    @GetMapping("/{id}")
    Shift TermById(@PathVariable Long id) {
        return TermRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    Shift save(@RequestBody Shift Term) {
        return TermRepository.save(Term);
    }

}
