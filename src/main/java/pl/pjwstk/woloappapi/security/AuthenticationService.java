package pl.pjwstk.woloappapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pjwstk.woloappapi.model.entities.User;
import pl.pjwstk.woloappapi.repository.UserRepository;
import pl.pjwstk.woloappapi.service.RoleService;
import pl.pjwstk.woloappapi.utils.EmailUtil;
import pl.pjwstk.woloappapi.utils.IllegalArgumentException;
import pl.pjwstk.woloappapi.utils.NotFoundException;
import pl.pjwstk.woloappapi.utils.UserMapper;

import java.io.IOException;
import java.util.Collections;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final EmailUtil emailUtil;

    @Transactional
    public AuthenticationResponse register(RegistrationRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("There is an account with that email address: " + request.getEmail());
        }
        var user = userMapper.toUser(request)
                .roles(Collections.singletonList(roleService.getRoleByName("USER")))
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtService.generateToken(user))
                .refreshToken(jwtService.generateRefreshToken(user))
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws AuthenticationException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("User email not found!"));
         return AuthenticationResponse.builder()
                .accessToken(jwtService.generateToken(user))
                .refreshToken(jwtService.generateRefreshToken(user))
                .build();
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String header = request.getHeader(AUTHORIZATION);
        if(header == null || !header.startsWith("Bearer ")){
            return;
        }
        final String refreshToken = header.substring(7);
        final String username = jwtService.extractUsername(refreshToken);
        if(username != null){
            var user = userRepository.findByEmail(username).orElseThrow();
            if(jwtService.isTokenValid(refreshToken, user)){
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(jwtService.generateToken(user))
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public void forgotPassword(String email) throws MessagingException {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("service method");
        userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User email not found!"));
        emailUtil.sendResetPasswordEmail(email);
    }

    public void setPassword(ForgotPasswordRequest request) {
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new NotFoundException("User email not found!"));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }


    public void changePassword(ChangePasswordRequest request, User user) {
        if(!passwordEncoder.matches(request.getOldPassword(), user.getPassword())){
            throw new IllegalArgumentException("Wrong password");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
