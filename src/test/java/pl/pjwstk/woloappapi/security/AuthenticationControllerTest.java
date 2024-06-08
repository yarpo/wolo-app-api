package pl.pjwstk.woloappapi.security;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import pl.pjwstk.woloappapi.TestDatabaseConfig;
import pl.pjwstk.woloappapi.model.entities.User;
import pl.pjwstk.woloappapi.repository.RoleRepository;
import pl.pjwstk.woloappapi.repository.UserRepository;
import pl.pjwstk.woloappapi.utils.EmailUtil;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {TestDatabaseConfig.class})
public class AuthenticationControllerTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Mock
    private EmailUtil emailUtil;

    private String token;
    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        var role = roleRepository.findByName("USER");

        var user = User.builder()
                .firstName("John")
                .lastName("John")
                .email("test.authenticationl@example.com")
                .password(passwordEncoder.encode("password"))
                .roles(List.of(role))
                .build();

        userRepository.save(user);

        token = jwtService.generateToken(user);
    }

    @Test
    public void testRegister() {
        RequestSpecification request = given();
        request.header("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        String registrationJson = """
            {
                "firstName": "John",
                "lastName": "Doe",
                "email": "john.doe@auth.example.com",
                "phoneNumber": "123456789",
                "isAdult": true,
                "password": "strongpassword123"
            }
        """;

        var response = request.body(registrationJson).post("/auth/register");

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        response.then().body("accessToken", notNullValue());
        response.then().body("refreshToken", notNullValue());
    }


    @Test
    public void testAuthenticationWithValidCredentials() {
        var validEmail = "admin@example.com";
        var validPassword = "password";

        given()
                .contentType("application/json")
                .body("{\"email\": \"" + validEmail + "\", \"password\": \"" + validPassword + "\"}")
                .when()
                .post("/auth/authenticate")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());
    }

    @Test
    public void testAuthenticationWithInvalidCredentials() {
        var invalidEmail = "invalid@example.com";
        var invalidPassword = "invalid123";

        given()
                .contentType("application/json")
                .body("{\"email\": \"" + invalidEmail + "\", \"password\": \"" + invalidPassword + "\"}")
                .when()
                .post("/auth/authenticate")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void testCurrentUserEndpoint() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .when()
                .get("/auth/current")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("email", equalTo("test.authenticationl@example.com"))
                .body("firstName", equalTo("John"))
                .body("lastName", equalTo("Doe"));
    }

    @Test
    public void testForgotPassword() throws Exception {
        doNothing().when(emailUtil).sendResetPasswordEmail(anyString());

        String jsonRequest = """
                {
                    "mail": "test.authenticationl@example.com"
                }
                """;

        given()
                .port(port)
                .header("Content-Type", "application/json")
                .body(jsonRequest)
                .when()
                .put("/auth/forgot-password")
                .then()
                .statusCode(HttpStatus.ACCEPTED.value());

        verify(emailUtil).sendResetPasswordEmail("test@example.com");
    }

    @Test
    public void testSetPassword() {
        String newPassword = "new_password";
        String jsonRequest = String.format("""
                {
                    "email": "test.authenticationl@example.com",
                    "password": "%s"
                }
                """, newPassword);

        given()
                .port(port)
                .header("Content-Type", "application/json")
                .body(jsonRequest)
                .when()
                .put("/auth/set-password")
                .then()
                .statusCode(HttpStatus.OK.value());

        var updatedUser = userRepository.findByEmail("test.authenticationl@example.com")
                .orElseThrow(() -> new RuntimeException("User not found"));
        assertTrue(passwordEncoder.matches(newPassword, updatedUser.getPassword()));
    }

    @Test
    public void testChangePassword() {
        String newPassword = "new_password";
        String jsonRequest = String.format("""
                {
                    "oldPassword": "password",
                    "newPassword": "%s"
                }
                """, newPassword);

        given()
                .port(port)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(jsonRequest)
                .when()
                .put("/auth/change-password")
                .then()
                .statusCode(HttpStatus.ACCEPTED.value());

        User updatedUser = userRepository.findByEmail("test@example.com").orElseThrow(() -> new RuntimeException("User not found"));
        assertTrue(passwordEncoder.matches(newPassword, updatedUser.getPassword()));
    }

    @Test
    public void testChangePasswordWrongOldPassword() {
        String newPassword = "new_password";
        String jsonRequest = String.format("""
                {
                    "oldPassword": "wrong_old_password",
                    "newPassword": "%s"
                }
                """, newPassword);

        given()
                .port(port)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(jsonRequest)
                .when()
                .put("/auth/change-password")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
