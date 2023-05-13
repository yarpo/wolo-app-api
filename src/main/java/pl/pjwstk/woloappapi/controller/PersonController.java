package pl.pjwstk.woloappapi.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.pjwstk.woloappapi.model.Person;
import pl.pjwstk.woloappapi.repository.PersonRepository;

@RestController
public class PersonController {



    PersonRepository repo;
    @PostMapping("/addPerson")
    public void addPerson(@RequestBody Person person){
        repo.save(person);
    }

}
