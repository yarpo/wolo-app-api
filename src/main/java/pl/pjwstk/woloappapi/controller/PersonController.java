package pl.pjwstk.woloappapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.pjwstk.woloappapi.model.entity.Person;
import pl.pjwstk.woloappapi.service.PersonService;

@RestController
@RequiredArgsConstructor
public class PersonController {

    final private PersonService personService;

    /**
     * To jest tu tymczasowo. Powinno zostać usunięte, gdy zacznie się używać właściwej metody po strinie frontendu
     */
    @PostMapping("/addPerson")
    @Deprecated
    public void addPerson(@RequestBody Person person) {
        this.createPerson(person);
    }

    @PostMapping("/persons")
    public Person createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }
}
