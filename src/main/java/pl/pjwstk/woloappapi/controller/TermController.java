package pl.pjwstk.woloappapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.pjwstk.woloappapi.model.Term;
import pl.pjwstk.woloappapi.repository.TermRepository;

@RestController
public class TermController  {

    private TermRepository TermRepository;

    @Autowired
    public TermController(TermRepository TermRepository) {
        this.TermRepository = TermRepository;
    }

    @GetMapping("/Term/all")
    Iterable<Term> all() {
        return TermRepository.findAll();
    }

    @GetMapping("/Term/{id}")
    Term TermById(@PathVariable Long id) {
        return TermRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND));
    }

    @PostMapping("/Term/save")
    Term save(@RequestBody Term Term) {
        return TermRepository.save(Term);
    }

}