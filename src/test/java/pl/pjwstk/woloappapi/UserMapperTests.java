package pl.pjwstk.woloappapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.*;
import pl.pjwstk.woloappapi.security.RegistrationRequest;
import pl.pjwstk.woloappapi.utils.UserMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserMapperTests {

    UserMapper userMapper = new UserMapper();

    @Test
    public void testToUserResponseDto(){
        User user = new User();
        user.setId(1L);
        user.setFirstname("Test Firstname");
        user.setLastname("Test Lastname");
        user.setAdult(true);
        user.setEmail("test@example.com");
        user.setPhoneNumber("123456789");
        user.setAgreementSigned(true);
        user.setPeselVerified(true);

        Role role1 = new Role();
        role1.setName("ROLE_USER");

        List<Role> roles = new ArrayList<>();
        roles.add(role1);
        user.setRoles(roles);

        UserResponseDto userResponseDto = userMapper.toUserResponseDto(user);

        assertEquals(Long.valueOf(1L), userResponseDto.getId());
        assertEquals("Test Firstname", userResponseDto.getFirstname());
        assertEquals("Test Lastname", userResponseDto.getLastname());
        assertTrue(userResponseDto.isAdult());
        assertEquals("test@example.com", userResponseDto.getEmail());
        assertEquals("123456789", userResponseDto.getPhoneNumber());
        assertTrue(userResponseDto.isAgreementSigned());
        assertTrue(userResponseDto.isPeselVerified());
        assertEquals(Collections.singletonList("ROLE_USER"), userResponseDto.getRoles());
    }

    @Test
    public void testToUser(){
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setFirstname("Test Firstname");
        registrationRequest.setLastname("Test Lastname");
        registrationRequest.setEmail("test@example.com");
        registrationRequest.setPhoneNumber("123456789");
        registrationRequest.setAdult(true);

        User user = userMapper.toUser(registrationRequest).build();

        assertEquals("Test Firstname", user.getFirstname());
        assertEquals("Test Lastname", user.getLastname());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("123456789", user.getPhoneNumber());
        assertTrue(user.isAdult());
        assertFalse(user.isAgreementSigned());
        assertFalse(user.isPeselVerified());
    }

    @Test
    public void testToUserShortRespons(){
        User userFull = new User();
        userFull.setFirstname("Test Firstname");
        userFull.setLastname("Test Lastname");
        userFull.setPhoneNumber("123456789");
        userFull.setEmail("test@example.com");

        UserShortRespons user = userMapper.toUserShortRespons(userFull);

        assertEquals("Test Firstname", user.getFirstname());
        assertEquals("Test Lastname", user.getLastname());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("123456789", user.getPhoneNumber());
    }
}
