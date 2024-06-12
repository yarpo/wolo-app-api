package pl.pjwstk.woloappapi.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.UserResponseDto;
import pl.pjwstk.woloappapi.model.UserShortResponse;
import pl.pjwstk.woloappapi.model.entities.*;
import pl.pjwstk.woloappapi.security.RegistrationRequest;
import pl.pjwstk.woloappapi.service.CityService;
import pl.pjwstk.woloappapi.service.DistrictService;
import pl.pjwstk.woloappapi.utils.EventMapper;
import pl.pjwstk.woloappapi.utils.UserMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserMapperTests {
    private EventMapper eventMapper;
    private UserMapper userMapper;

    @BeforeEach
    public void setup() {
        DistrictService districtService = Mockito.mock(DistrictService.class);
        CityService cityService = Mockito.mock(CityService.class);
        eventMapper = new EventMapper(districtService, cityService);
        userMapper = new UserMapper(eventMapper);
    }

    @Test
    public void testToUserResponseDto() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Test Firstname");
        user.setLastName("Test Lastname");
        user.setEmail("test@example.com");
        user.setPhoneNumber("123456789");
        user.setPeselVerified(true);
        user.setAgreementSigned(true);
        user.setAdult(true);

        Role role = new Role();
        role.setName("ROLE_USER");

        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);

        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Organisation Name");

        user.setOrganisation(organisation);

        Shift shift = new Shift();
        shift.setId(1L);
        Address address = new Address();
        address.setStreet("Test Street");
        shift.setAddress(address);
        Event event = new Event();
        event.setDate(LocalDate.now());
        shift.setEvent(event);

        ShiftToUser shiftToUser = new ShiftToUser();
        shiftToUser.setId(1L);
        shiftToUser.setShift(shift);

        List<ShiftToUser> shifts = new ArrayList<>();
        shifts.add(shiftToUser);

        user.setShifts(shifts);

        UserResponseDto userResponseDto = userMapper.toUserResponseDto(user);

        assertEquals(Long.valueOf(1L), userResponseDto.getId());
        assertEquals("Test Firstname", userResponseDto.getFirstName());
        assertEquals("Test Lastname", userResponseDto.getLastName());
        assertTrue(userResponseDto.isAdult());
        assertEquals("test@example.com", userResponseDto.getEmail());
        assertEquals("123456789", userResponseDto.getPhoneNumber());
        assertTrue(userResponseDto.isAgreementSigned());
        assertTrue(userResponseDto.isPeselVerified());
        assertEquals(Collections.singletonList("ROLE_USER"), userResponseDto.getRoles());
        assertEquals(1L, userResponseDto.getOrganisationId());
        assertEquals("Organisation Name", userResponseDto.getOrganisationName());
        assertEquals(1, userResponseDto.getShifts().size());
    }
    @Test
    public void testToUser(){
        UserMapper userMapper = new UserMapper(eventMapper);
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setFirstName("Test Firstname");
        registrationRequest.setLastName("Test Lastname");
        registrationRequest.setEmail("test@example.com");
        registrationRequest.setPhoneNumber("123456789");
        registrationRequest.setIsAdult(true);

        User user = userMapper.toUser(registrationRequest).build();

        assertEquals("Test Firstname", user.getFirstName());
        assertEquals("Test Lastname", user.getLastName());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("123456789", user.getPhoneNumber());
        assertTrue(user.isAdult());
        assertFalse(user.isAgreementSigned());
        assertFalse(user.isPeselVerified());
    }

    @Test
    public void testToUserShortRespons(){
        UserMapper userMapper = new UserMapper(eventMapper);
        User userFull = new User();
        userFull.setId(1L);
        userFull.setFirstName("Test Firstname");
        userFull.setLastName("Test Lastname");
        userFull.setPhoneNumber("123456789");
        userFull.setEmail("test@example.com");

        UserShortResponse user = userMapper.toUserShortRespons(userFull);

        assertEquals(1L, user.getId());
        assertEquals("Test Firstname", user.getFirstName());
        assertEquals("Test Lastname", user.getLastName());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("123456789", user.getPhoneNumber());
    }
}
