package pl.pjwstk.woloappapi.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.ShiftInfoRespons;
import pl.pjwstk.woloappapi.model.ShiftResponseDto;
import pl.pjwstk.woloappapi.model.UserResponseDto;
import pl.pjwstk.woloappapi.model.UserShortResponse;
import pl.pjwstk.woloappapi.model.entities.*;
import pl.pjwstk.woloappapi.security.RegistrationRequest;
import pl.pjwstk.woloappapi.utils.EventMapper;
import pl.pjwstk.woloappapi.utils.UserMapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserMapperTests {

    private EventMapper eventMapper;

    /*
    @Test
    public void testToUserResponseDto(){
        EventMapper eventMapper = new EventMapper();
        UserMapper userMapper = new UserMapper(eventMapper);
        User user = new User();
        user.setId(1L);
        user.setFirstName("Test Firstname");
        user.setLastName("Test Lastname");
        user.setAdult(true);
        user.setEmail("test@example.com");
        user.setPhoneNumber("123456789");
        user.setAgreementSigned(true);
        user.setPeselVerified(true);

        Role role = new Role();
        role.setName("ROLE_USER");

        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);

        Event event = new Event();
        event.setId(1L);

        Address address = new Address();

        AddressToEvent addressToEvent = new AddressToEvent();
        addressToEvent.setEvent(event);
        addressToEvent.setAddress(address);

        Shift shift = new Shift();
        shift.setId(1L);
        shift.setAddressToEvent(addressToEvent);

        ShiftToUser shiftToUser = new ShiftToUser();
        shiftToUser.setId(1L);

        List<ShiftToUser> shifts = new ArrayList<>();
        shifts.add(shiftToUser);
        user.setShifts(shifts);

        ShiftInfoRespons shiftInfoRespons = new ShiftInfoRespons();
        shiftInfoRespons.setId(1L);

        eventMapper.toShiftInfoRespons(shift);

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
    }
    */

    @Test
    public void testToShiftResponseDto(){
        EventMapper eventMapper = new EventMapper();
        UserMapper userMapper = new UserMapper(eventMapper);
        Event event = new Event();
        event.setName("Test Event");
        event.setId(1L);

        User user = new User();
        user.setId(1L);


        List<AddressToEvent> addressToEvents = new ArrayList<>();

        Shift shift = new Shift();
        shift.setId(1L);
        shift.setStartTime(LocalTime.of(9, 0));
        shift.setEndTime(LocalTime.of(17, 0));
        shift.setDate(LocalDate.now());
        shift.setRegisteredUsers(1);
        shift.setCapacity(10);
        shift.setLeaderRequired(true);
        shift.setRequiredMinAge(18);

        District district = new District();
        district.setName("Test Name");
        district.setCity("Test City");

        Address address = new Address();
        address.setStreet("Test Street");
        address.setHomeNum("123");
        address.setDistrict(district);

        AddressToEvent addressToEvent = new AddressToEvent();
        addressToEvent.setAddress(address);
        addressToEvent.setEvent(event);
        shift.setAddressToEvent(addressToEvent);

        List<Shift> shiftList = new ArrayList<>();
        shiftList.add(shift);

        addressToEvent.setShifts(shiftList);
        addressToEvents.add(addressToEvent);

        event.setAddressToEvents(addressToEvents);

        ShiftToUser shiftToUser = new ShiftToUser();
        shiftToUser.setId(1L);
        shiftToUser.setUser(user);
        shiftToUser.setShift(shift);
        shiftToUser.setLeader(false);
        ShiftResponseDto shiftResponseDto = userMapper.toShiftResponseDto(shiftToUser);

        assertEquals(1L, shiftResponseDto.getShiftId());
        assertEquals(1L, shiftResponseDto.getEventId());
        assertEquals("Test Event", shiftResponseDto.getName());
        assertEquals(LocalTime.of(9, 0), shiftResponseDto.getStartTime());
        assertEquals(LocalTime.of(17, 0), shiftResponseDto.getEndTime());
        assertEquals(LocalDate.now(), shiftResponseDto.getDate());
    }

    @Test
    public void testToUser(){
        UserMapper userMapper = new UserMapper(eventMapper);
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setFirstName("Test Firstname");
        registrationRequest.setLastName("Test Lastname");
        registrationRequest.setEmail("test@example.com");
        registrationRequest.setPhoneNumber("123456789");
        registrationRequest.setAdult(true);

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
        userFull.setFirstName("Test Firstname");
        userFull.setLastName("Test Lastname");
        userFull.setPhoneNumber("123456789");
        userFull.setEmail("test@example.com");

        UserShortResponse user = userMapper.toUserShortRespons(userFull);

        assertEquals("Test Firstname", user.getFirstName());
        assertEquals("Test Lastname", user.getLastName());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("123456789", user.getPhoneNumber());
    }
}
