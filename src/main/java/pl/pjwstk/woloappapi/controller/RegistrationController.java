package pl.pjwstk.woloappapi.controller;



import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjwstk.woloappapi.model.User;
import pl.pjwstk.woloappapi.repository.UserRepository;


@RestController
@AllArgsConstructor
public class RegistrationController {

    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registrationSubmit(@ModelAttribute User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("E-mail is already taken.");
        }

        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful. You can now log in.");
    }

}
