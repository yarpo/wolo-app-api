package pl.pjwstk.woloappapi.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.ShiftResponseDto;
import pl.pjwstk.woloappapi.model.UserResponseDto;
import pl.pjwstk.woloappapi.model.UserShortResponse;
import pl.pjwstk.woloappapi.model.entities.*;
import pl.pjwstk.woloappapi.security.RegistrationRequest;
import pl.pjwstk.woloappapi.service.CityService;
import pl.pjwstk.woloappapi.service.DistrictService;
import pl.pjwstk.woloappapi.utils.EventMapper;
import pl.pjwstk.woloappapi.utils.UserMapper;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserMapperTests {

    private DistrictService districtService;
    private CityService cityService;
    private EventMapper eventMapper;

    @Test
    public void testToUserResponseDto(){
        EventMapper eventMapper = new EventMapper(districtService, cityService);
        UserMapper userMapper = new UserMapper(eventMapper);
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

        Event event = new Event();
        Address address = new Address();

        Shift shift = new Shift();
        shift.setId(1L);
        shift.setEvent(event);
        shift.setAddress(address);

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
	/*

    @Test
    public void testToShiftResponseDto(){
        EventMapper eventMapper = new EventMapper(districtService, cityService);
        Event event = new Event();
        event.setNameEN("Test Event");
        event.setId(1L);

        User user = new User();
        user.setId(1L);

        Shift shift = new Shift();
        shift.setId(1L);
        shift.setEvent(event);
        shift.setStartTime(LocalTime.of(9, 0));
        shift.setEndTime(LocalTime.of(17, 0));

        shift.setLeaderRequired(true);
        shift.setRequiredMinAge(18);
        shift.setRegisteredUsers(1);
        shift.setShiftDirectionsEN("Test Directions");

        District district = new District();
        district.setName("Test Name");

        Address address = new Address();
        address.setStreet("Test Street");
        address.setHomeNum("123");
        address.setDistrict(district);
        shift.setAddress(address);

        ShiftToUser shiftToUser = new ShiftToUser();
        shiftToUser.setId(1L);
        shiftToUser.setUser(user);
        shiftToUser.setShift(shift);
        ShiftResponseDto shiftResponseDto = eventMapper.toShiftResponseDto(shiftToUser.getShift());

        assertEquals(1L, shiftResponseDto.getShiftId());
        assertEquals(1L, shiftResponseDto.getEventId());
        assertEquals(LocalTime.of(9, 0), shiftResponseDto.getStartTime());
        assertEquals(LocalTime.of(17, 0), shiftResponseDto.getEndTime());
    }
	*/

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
