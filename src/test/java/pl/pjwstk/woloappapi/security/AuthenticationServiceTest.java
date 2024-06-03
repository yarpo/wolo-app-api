package pl.pjwstk.woloappapi.security;

import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.pjwstk.woloappapi.model.entities.Role;
import pl.pjwstk.woloappapi.model.entities.User;
import pl.pjwstk.woloappapi.repository.UserRepository;
import pl.pjwstk.woloappapi.service.RoleService;
import pl.pjwstk.woloappapi.service.security.*;
import pl.pjwstk.woloappapi.utils.EmailUtil;
import pl.pjwstk.woloappapi.utils.IllegalArgumentException;
import pl.pjwstk.woloappapi.utils.OtpUtil;
import pl.pjwstk.woloappapi.utils.UserMapper;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private OtpUtil otpUtil;

    @Mock
    private EmailUtil emailUtil;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void testRegister_UserAlreadyExists_ExceptionThrown() {
        var request = RegistrationRequest.builder()
                .email("test@example.com")
                .password("password")
                .build();

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> authenticationService.register(request));
        assertEquals("There is an account with that email address: test@example.com", exception.getMessage());
    }

    @Test
    void testRegister_NewUser_Success() throws MessagingException {
        var request = RegistrationRequest.builder()
                .email("test@example.com")
                .password("password")
                .build();
        var role = Role.builder().id(1L).name("USER").build();
        var otp = "123456";

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(otpUtil.generateOtp()).thenReturn(otp);
        when(roleService.getRoleByName("USER")).thenReturn(role);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(userMapper.toUser(request)).thenReturn(User.builder().email(request.getEmail()));

        doNothing().when(emailUtil).sendOtpMail(request.getEmail(), otp);

        String response = authenticationService.register(request);

        assertEquals("Account successfully has been created. For account verification use link sent to you in email", response);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegister_EmailSendingFailure_RuntimeExceptionThrown() throws MessagingException {
        var request = RegistrationRequest.builder()
                .email("test@example.com")
                .password("password")
                .build();
        var otp = "123456";

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(otpUtil.generateOtp()).thenReturn(otp);

        doThrow(new MessagingException("Email send failure")).when(emailUtil).sendOtpMail(request.getEmail(), otp);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authenticationService.register(request));
        assertEquals("Unable to send email", exception.getMessage());

        verify(userRepository, never()).save(any());
    }

    @Test
    void testAuthenticate_ValidCredentials_Success() {
        var request = new AuthenticationRequest("test@example.com", "password");
        var user = new User();
        when(userRepository.findByEmail(request.getEmail())).thenReturn(java.util.Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(user)).thenReturn("refreshToken");
        when(authenticationManager.authenticate(any())).thenReturn(new Authentication() {
            @Override
            public boolean equals(Object another) {
                return false;
            }

            @Override
            public String toString() {
                return null;
            }

            @Override
            public int hashCode() {
                return 0;
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws java.lang.IllegalArgumentException {

            }
        });
        AuthenticationResponse response = authenticationService.authenticate(request);

        assertNotNull(response.getAccessToken());
        assertNotNull(response.getRefreshToken());
        verify(jwtService, times(1)).generateToken(user);
        verify(jwtService, times(1)).generateRefreshToken(user);
    }

}
