package pl.pjwstk.woloappapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.pjwstk.woloappapi.model.entity.Person;
import pl.pjwstk.woloappapi.service.PersonService;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WoloAppApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PersonService personService;

    @BeforeEach
    void setUp() {
        reset(personService);
    }

    // wykomentowyje tymczasowo, póki nie poprawi się błędu z bazą danych dla testów
    // @Test
    public void testHealthEndpoint() {
        given()
                .when().get("/health")
                .then()
                .statusCode(200);
    }

    // wykomentowyje tymczasowo, póki nie poprawi się błędu z bazą danych dla testów
    // @Test
    void testCreatePerson() throws Exception {
        // Arrange
        Person person = new Person(111, "Doe");
        when(personService.createPerson(person)).thenReturn(person);

        // Act
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\"}")
                ).andExpect(status().isOk())
                .andReturn();

        // Assert
        verify(personService, times(1)).createPerson(person);
        result.getResponse().getContentAsString().contains("Doe");
    }
}
