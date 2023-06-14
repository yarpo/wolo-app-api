package pl.pjwstk.woloappapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.pjwstk.woloappapi.model.entity.Person;
import pl.pjwstk.woloappapi.repository.PersonRepository;

@Component
@RequiredArgsConstructor
public class PersonService {

    final private PersonRepository personRepository;

    public Person createPerson(Person newPerson) {
        if (newPerson == null) {
            throw new IllegalArgumentException("Person cannot be null");
        }

        // tu jeszcze może być walidacja

        // być może powinniśmy zwracać DTO zamiast entity, ale póki co może tak być
        return personRepository.save(newPerson);
    }
}
