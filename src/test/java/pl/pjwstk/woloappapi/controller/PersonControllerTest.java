package pl.pjwstk.woloappapi.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.entity.Person;
import pl.pjwstk.woloappapi.service.PersonService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

    public static final Person input = new Person(0, "John");

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    @BeforeEach
    public void setup() {
        reset(personService);
    }

    @Test
    void addPersonShouldCallPersonService() {
        personController.addPerson(input);
        verify(personService).createPerson(input);
    }


    @Test
    void createPersonShouldCallPersonService() {
        personController.createPerson(input);
        verify(personService).createPerson(input);
    }
}