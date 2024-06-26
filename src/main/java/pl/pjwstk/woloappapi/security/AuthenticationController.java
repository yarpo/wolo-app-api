package pl.pjwstk.woloappapi.security;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.pjwstk.woloappapi.model.UserResponseDto;
import pl.pjwstk.woloappapi.service.UserService;
import pl.pjwstk.woloappapi.utils.UserMapper;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegistrationRequest request){
        return new ResponseEntity<>(authenticationService.register(request), HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest request){
        return new ResponseEntity<>(authenticationService.authenticate(request), HttpStatus.OK);
    }

    @PutMapping("/verify-account")
    public ResponseEntity<String> verifyAccount(@RequestParam String email, @RequestParam String otp){
        return new ResponseEntity<>(authenticationService.verifyAccount(email, otp), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public void refresh(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }

    @GetMapping("/current")
    public ResponseEntity<UserResponseDto> current(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = userService.getCurrentUser(authentication);
        var userResponse = userMapper.toUserResponseDto(user);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PutMapping("/forgot-password")
    public ResponseEntity<HttpStatus> forgotPassword(@RequestBody ForgePasswordMailRequest request){
        try {
            authenticationService.forgotPassword(request.getMail());
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send reset password email");
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/set-password")
    public ResponseEntity<HttpStatus> setPassword(@RequestBody ForgotPasswordRequest request){
        authenticationService.setPassword(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/change-password")
    public ResponseEntity<HttpStatus> changePassword(@RequestBody ChangePasswordRequest request){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = userService.getCurrentUser(authentication);
        authenticationService.changePassword(request, user);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/regenerate-otp")
    public ResponseEntity<String> regenerateOtp(@RequestParam String email){
        return new ResponseEntity<>(authenticationService.regenerateOtp(email), HttpStatus.OK);
    }
}
