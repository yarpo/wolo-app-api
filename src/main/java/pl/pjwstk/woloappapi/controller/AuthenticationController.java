package pl.pjwstk.woloappapi.controller;



import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import pl.pjwstk.woloappapi.model.LoginDto;
import pl.pjwstk.woloappapi.model.RegisterDto;
import pl.pjwstk.woloappapi.model.Role;
import pl.pjwstk.woloappapi.model.UserEntity;
import pl.pjwstk.woloappapi.repository.RoleRepository;
import pl.pjwstk.woloappapi.repository.UserRepository;
import pl.pjwstk.woloappapi.security.JWTGenerator;

import java.util.Collection;

import static pl.pjwstk.woloappapi.security.CustomUserDetailsService.logger;

@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/secured")
    public String secured(){
        return "secured";
    }
    @GetMapping("/login")
    public String get_login(){
        return "login";
    }
    @PostMapping("/login_endpoint")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

            // Set the authenticated user in the SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authentication);

            logGrantedAuthorities(authentication);
            // Return a success response
            return new ResponseEntity<>("User login successful", HttpStatus.OK);
        } catch (AuthenticationException e) {
            // Handle authentication failure
            return new ResponseEntity<>("Authentication failed", HttpStatus.UNAUTHORIZED);
        }
    }
    private void logGrantedAuthorities(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

            // Log the granted authorities
            authorities.forEach(authority ->
                    logger.info("User '{}' has authority: {}", userDetails.getUsername(), authority.getAuthority()));
        }
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("E-mail is already taken.");
        }
        UserEntity user = new UserEntity();
        user.setEmail(registerDto.getEmail());
        user.setFirstname("John");
        user.setLastname("Smith");
        user.setPeselVerified(false);
        user.setAdult(false);
        user.setAgreementSigned(false);
        user.setSalt("salt");
        user.setPhoneNumber("123456789");
        user.setPassword_hash(passwordEncoder.encode(registerDto.getPassword()));
        Role role = roleRepository.findByName("Użytkownik");
        user.setRole(role);

        userRepository.save(user);

        return new ResponseEntity<>("User succesfuly registered", HttpStatus.OK);
    }
    @PostMapping("/google_success")
    public String success(HttpServletResponse response) {
        JWTGenerator jwtGenerator = new JWTGenerator();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = jwtGenerator.generateToken(authentication);
        response.addHeader("Authorization", "Bearer " + token);
        return "redirect:/dashboard";
    }
}
