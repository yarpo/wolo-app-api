package pl.pjwstk.woloappapi.service.security;

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
import pl.pjwstk.woloappapi.model.entities.User;
import pl.pjwstk.woloappapi.repository.UserRepository;
import pl.pjwstk.woloappapi.service.RoleService;
import pl.pjwstk.woloappapi.utils.IllegalArgumentException;
import pl.pjwstk.woloappapi.utils.*;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
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
    private final OtpUtil otpUtil;


    public String register(RegistrationRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("There is an account with that email address: " + request.getEmail());
        }
        var otp = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpMail(request.getEmail(), otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send email");
        }
        var user = userMapper.toUser(request)
                .roles(Collections.singletonList(roleService.getRoleByName("USER")))
                .password(passwordEncoder.encode(request.getPassword()))
                .otp(otp)
                .otpGeneratedTime(LocalDateTime.now())
                .build();
        userRepository.save(user);
        return "Account successfully has been created. For account verification use link sent to you in email";
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws AuthenticationException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("User email not found!"));
        if(!user.isActive()){
            throw new RuntimeException("Your account is not verified");
        }
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

    public String verifyAccount(String email, String otp) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with this email: " + email));
        if (user.getOtp().equals(otp) && Duration.between(user.getOtpGeneratedTime(),
                LocalDateTime.now()).getSeconds() < (30 * 60)) {
            user.setActive(true);
            userRepository.save(user);
            return "Account verified you can login";
        }
        return "Verification link was expired";
    }

    public String regenerateOtp(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with this email: " + email));
        var otp = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpMail(email, otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send email");
        }
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "Email sent, please verify account within 30 minutes";
    }
}
