package pl.pjwstk.woloappapi.integrationTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.pjwstk.woloappapi.model.entities.Privilege;
import pl.pjwstk.woloappapi.model.entities.Role;
import pl.pjwstk.woloappapi.model.entities.User;
import pl.pjwstk.woloappapi.repository.RoleRepository;
import pl.pjwstk.woloappapi.repository.UserRepository;
import pl.pjwstk.woloappapi.security.JwtService;
import pl.pjwstk.woloappapi.utils.EmailUtil;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmailUtil emailUtil;

    private MockMvc mockMvc;
    private String token;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        var roleUser = Role.builder()
                .id(1L)
                .name("USER")
                .privileges(List.of(new Privilege()))
                .build();

        var user = User.builder()
                .firstName("John")
                .lastName("John")
                .email("test.authenticationl@example.com")
                .password(passwordEncoder.encode("password"))
                .roles(List.of(roleUser))
                .build();

        token = jwtService.generateToken(user);

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
    }

    @Test
    public void testRegister() throws Exception {
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

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registrationJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists());
    }


//    @Test
//    public void testAuthenticationWithValidCredentials() {
//        var validEmail = "admin@example.com";
//        var validPassword = "password";
//
//        given()
//                .contentType("application/json")
//                .body("{\"email\": \"" + validEmail + "\", \"password\": \"" + validPassword + "\"}")
//                .when()
//                .post("/auth/authenticate")
//                .then()
//                .statusCode(HttpStatus.OK.value())
//                .body("accessToken", notNullValue())
//                .body("refreshToken", notNullValue());
//    }
//
//    @Test
//    public void testAuthenticationWithInvalidCredentials() {
//        var invalidEmail = "invalid@example.com";
//        var invalidPassword = "invalid123";
//
//        given()
//                .contentType("application/json")
//                .body("{\"email\": \"" + invalidEmail + "\", \"password\": \"" + invalidPassword + "\"}")
//                .when()
//                .post("/auth/authenticate")
//                .then()
//                .statusCode(HttpStatus.UNAUTHORIZED.value());
//    }
//
//    @Test
//    public void testCurrentUserEndpoint() {
//        RestAssured.given()
//                .header("Authorization", "Bearer " + token)
//                .contentType(ContentType.JSON)
//                .when()
//                .get("/auth/current")
//                .then()
//                .statusCode(HttpStatus.OK.value())
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body("email", equalTo("test.authenticationl@example.com"))
//                .body("firstName", equalTo("John"))
//                .body("lastName", equalTo("Doe"));
//    }
//
//    @Test
//    public void testForgotPassword() throws Exception {
//        doNothing().when(emailUtil).sendResetPasswordEmail(anyString());
//
//        String jsonRequest = """
//                {
//                    "mail": "test.authenticationl@example.com"
//                }
//                """;
//
//        given()
//                .port(port)
//                .header("Content-Type", "application/json")
//                .body(jsonRequest)
//                .when()
//                .put("/auth/forgot-password")
//                .then()
//                .statusCode(HttpStatus.ACCEPTED.value());
//
//        verify(emailUtil).sendResetPasswordEmail("test@example.com");
//    }
//
//    @Test
//    public void testSetPassword() {
//        String newPassword = "new_password";
//        String jsonRequest = String.format("""
//                {
//                    "email": "test.authenticationl@example.com",
//                    "password": "%s"
//                }
//                """, newPassword);
//
//        given()
//                .port(port)
//                .header("Content-Type", "application/json")
//                .body(jsonRequest)
//                .when()
//                .put("/auth/set-password")
//                .then()
//                .statusCode(HttpStatus.OK.value());
//
//        var updatedUser = userRepository.findByEmail("test.authenticationl@example.com")
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        assertTrue(passwordEncoder.matches(newPassword, updatedUser.getPassword()));
//    }
//
//    @Test
//    public void testChangePassword() {
//        String newPassword = "new_password";
//        String jsonRequest = String.format("""
//                {
//                    "oldPassword": "password",
//                    "newPassword": "%s"
//                }
//                """, newPassword);
//
//        given()
//                .port(port)
//                .header("Content-Type", "application/json")
//                .header("Authorization", "Bearer " + token)
//                .body(jsonRequest)
//                .when()
//                .put("/auth/change-password")
//                .then()
//                .statusCode(HttpStatus.ACCEPTED.value());
//
//        User updatedUser = userRepository.findByEmail("test@example.com").orElseThrow(() -> new RuntimeException("User not found"));
//        assertTrue(passwordEncoder.matches(newPassword, updatedUser.getPassword()));
//    }
//
//    @Test
//    public void testChangePasswordWrongOldPassword() {
//        String newPassword = "new_password";
//        String jsonRequest = String.format("""
//                {
//                    "oldPassword": "wrong_old_password",
//                    "newPassword": "%s"
//                }
//                """, newPassword);
//
//        given()
//                .port(port)
//                .header("Content-Type", "application/json")
//                .header("Authorization", "Bearer " + token)
//                .body(jsonRequest)
//                .when()
//                .put("/auth/change-password")
//                .then()
//                .statusCode(HttpStatus.BAD_REQUEST.value());
//    }
}
