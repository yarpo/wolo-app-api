package pl.pjwstk.woloappapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.entity.Person;
import pl.pjwstk.woloappapi.repository.PersonRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @BeforeEach
    public void setUp() {
        reset(personRepository);
    }

    @Test
    public void testCreatePersonShouldSaveDataWhenValidDataGiven() {
        Person input = new Person(0, "John");
        Person saved = new Person(111, "John");
        when(personRepository.save(input)).thenReturn(saved);

        Person actual = personService.createPerson(input);

        assertEquals(saved, actual);
        verify(personRepository).save(input);
    }

    @Test
    public void testCreatePersonShouldThrowExceptionWhenNull() {
        IllegalArgumentException expectedException = assertThrows(
                IllegalArgumentException.class, () -> personService.createPerson(null)
        );
        assertEquals("Person cannot be null", expectedException.getMessage());
    }
}