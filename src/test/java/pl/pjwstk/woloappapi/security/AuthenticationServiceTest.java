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
import pl.pjwstk.woloappapi.utils.EmailUtil;
import pl.pjwstk.woloappapi.utils.IllegalArgumentException;
import pl.pjwstk.woloappapi.utils.NotFoundException;
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
    private EmailUtil emailUtil;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void testRegister_NewUser_Success() {
        var request = RegistrationRequest.builder()
                .email("test@example.com")
                .password("password")
                .build();
        var role = Role.builder()
                .id(1L)
                .name("USER")
                .build();
        var user = User.builder();
        when(userMapper.toUser(request)).thenReturn(user);
        when(roleService.getRoleByName("USER")).thenReturn(role);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);

        authenticationService.register(request);

        verify(jwtService, times(1)).generateToken(user.build());
        verify(jwtService, times(1)).generateRefreshToken(user.build());
    }

    @Test
    void testRegister_UserAlreadyExists_ExceptionThrown() {
        var request = RegistrationRequest.builder()
                .email("test@example.com")
                .password("password")
                .build();
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> authenticationService.register(request));
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

    @Test
    void testForgotPassword_EmailSent_Success() throws MessagingException {
        var email = "test@example.com";

        authenticationService.forgotPassword(email);

        verify(emailUtil, times(1)).sendResetPasswordEmail(email);
    }

    @Test
    void testSetPassword_UserExists_Success() {
        var email = "test@example.com";
        var newPassword = "newPassword";
        var request = new ForgotPasswordRequest(email, newPassword);
        var user = User.builder().email(email).build();

        when(userRepository.findByEmail(email)).thenReturn(java.util.Optional.of(user));
        when(passwordEncoder.encode(newPassword)).thenReturn("encodedNewPassword");

        authenticationService.setPassword(request);

        assertEquals("encodedNewPassword", user.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testSetPassword_UserNotFound_ExceptionThrown() {
        var email = "test@example.com";
        var newPassword = "newPassword";
        var request = new ForgotPasswordRequest(email, newPassword);

        when(userRepository.findByEmail(email)).thenReturn(java.util.Optional.empty());

        assertThrows(NotFoundException.class, () -> authenticationService.setPassword(request));
        verify(userRepository, never()).save(any());
    }

    @Test
    void testChangePassword_OldPasswordMatches_Success() {
        var oldPassword = "oldPassword";
        var newPassword = "newPassword";
        var request = new ChangePasswordRequest(oldPassword, newPassword);
        var user = User.builder().password("encodedOldPassword").build();

        when(passwordEncoder.matches(oldPassword, user.getPassword())).thenReturn(true);
        when(passwordEncoder.encode(newPassword)).thenReturn("encodedNewPassword");

        authenticationService.changePassword(request, user);

        assertEquals("encodedNewPassword", user.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testChangePassword_OldPasswordNotMatches_ExceptionThrown() {
        var oldPassword = "oldPassword";
        var newPassword = "newPassword";
        var request = new ChangePasswordRequest(oldPassword, newPassword);
        var user = User.builder().password("encodedOldPassword").build();

        when(passwordEncoder.matches(oldPassword, user.getPassword())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> authenticationService.changePassword(request, user));
        verify(userRepository, never()).save(any());
    }

}
