package pl.pjwstk.woloappapi.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.pjwstk.woloappapi.model.Person;
import pl.pjwstk.woloappapi.repository.PersonRepository;

@RestController
public class PersonController {

    private PersonRepository  personRepository;

    @Autowired
    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/person/all")
    Iterable<Person> all() {
        return personRepository.findAll();
    }

    @GetMapping("/person/{id}")
    Person personById(@PathVariable Long id) {
        return personRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND));
    }

    @PostMapping("/person/save")
    Person save(@RequestBody Person person) {
        return personRepository.save(person);
    }

}