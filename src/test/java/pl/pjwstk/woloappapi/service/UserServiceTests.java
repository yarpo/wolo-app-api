package pl.pjwstk.woloappapi.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.UserRequestDto;
import pl.pjwstk.woloappapi.model.entities.*;
import pl.pjwstk.woloappapi.repository.ShiftToUserRepository;
import pl.pjwstk.woloappapi.repository.UserRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private RoleService roleService;

    @Mock
    private ShiftService shiftService;

    @Mock
    private OrganisationService organisationService;

    @Mock
    private ShiftToUserRepository shiftToUserRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testGetAllUsers(){
        User user1 = new User();
        User user2 = new User();

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> actualUsers = userService.getAllUsers();

        assertEquals(2, actualUsers.size());
    }

    @Test
    public void testGetUserById(){
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User retrievedUser = userService.getUserById(1L);

        assertEquals(user.getId(), retrievedUser.getId());
    }

    @Test
    public void testDeleteUserWhenModeratorOfOrganisation() {
        User user = new User();
        user.setId(1L);
        Organisation organisation = new Organisation();
        organisation.setName("Test Organisation");
        user.setOrganisation(organisation);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.deleteUser(1L);
        });

        assertTrue(thrown.getMessage().contains("firstly assign new moderator to organisation"));

        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    public void testDeleteUserWhenOnlyAdmin() {
        User user = new User();
        user.setId(1L);
        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        user.setRoles(Collections.singletonList(adminRole));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        when(roleService.getRoleByName("ADMIN")).thenReturn(adminRole);
        when(userRepository.findUsersByRole("ADMIN")).thenReturn(Collections.singletonList(user));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.deleteUser(1L);
        });

        assertTrue(thrown.getMessage().contains("to remove it, first create another administrator"));

        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setId(1L);
        user.setRoles(new ArrayList<>());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    /*
    @Test
    public void testUpdateUser() {
        UserRequestDto userDto = new UserRequestDto();
        userDto.setFirstName("Test Firstname");
        userDto.setLastName("Test Lastname");
        userDto.setEmail("test@example.com");
        userDto.setPhoneNumber("123456789");
        userDto.setPeselVerified(true);
        userDto.setAgreementSigned(true);
        userDto.setAdult(true);

        User user = new User();
        user.setId(1L);
        user.setFirstName("Firstname Test");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.updateUser(userDto, 1L);

        verify(userRepository, times(1)).findById(1L);
        assertEquals("Test Firstname", user.getFirstName());
        assertEquals(userDto.getFirstName(), user.getFirstName());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getPhoneNumber(), user.getPhoneNumber());
        assertEquals(userDto.isPeselVerified(), user.isPeselVerified());
        assertEquals(userDto.isAgreementSigned(), user.isAgreementSigned());
        assertEquals(userDto.isAdult(), user.isAdult());
    }*/

    @Test
    public void testUpdateUserRoles(){
        List<Long> rolesToUpdate = new ArrayList<>();
        rolesToUpdate.add(2L);
        rolesToUpdate.add(3L);

        User user = new User();
        user.setId(1L);
        Role role1 = new Role();
        role1.setId(1L);
        role1.setName("Role1");
        Role role2 = new Role();
        role2.setId(2L);
        role2.setName("Role2");
        Role role3 = new Role();
        role3.setId(3L);
        role3.setName("Role3");

        user.setRoles(new ArrayList<>(List.of(role1)));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleService.getRoleById(2L)).thenReturn(role2);
        when(roleService.getRoleById(3L)).thenReturn(role3);

        // When
        userService.updateUserRoles(1L, rolesToUpdate);

        // Then
        verify(userRepository, times(1)).findById(1L);
        assertEquals(2, user.getRoles().size());
        assertEquals("Role2", user.getRoles().get(0).getName());
        assertEquals("Role3", user.getRoles().get(1).getName());
    }

    @Test
    public void testJoinEvent() {
        User user = new User();
        user.setId(1L);
        Shift shift = new Shift();
        shift.setId(1L);
        shift.setCapacity(10);
        shift.setRegisteredUsers(5);
        shift.setShiftToUsers(new ArrayList<>());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(shiftService.getShiftById(1L)).thenReturn(shift);

        userService.joinEvent(1L, 1L, false);

        assertEquals(6, shift.getRegisteredUsers());
        assertEquals(1, shift.getShiftToUsers().size());
        verify(shiftService, times(1)).editShift(shift);
    }

    @Test
    public void testJoinEvent_ShiftFull() {
        User user = new User();
        user.setId(1L);
        Shift shift = new Shift();
        shift.setId(1L);
        shift.setCapacity(5);
        shift.setRegisteredUsers(5);
        shift.setShiftToUsers(new ArrayList<>());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(shiftService.getShiftById(1L)).thenReturn(shift);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.joinEvent(1L, 1L, false);
        });

        assertEquals("The event is fully booked", exception.getMessage());
        assertEquals(5, shift.getRegisteredUsers());
        assertEquals(0, shift.getShiftToUsers().size());
    }

    @Test
    public void testAssignOrganisation_WithModerator() {
        Organisation organisation = new Organisation();
        organisation.setId(1L);

        User user = new User();
        user.setId(1L);

        Role role = new Role();
        role.setId(1L);
        role.setName("MODERATOR");
        user.setRoles(new ArrayList<>(List.of(role)));

        organisation.setModerator(user);

        when(organisationService.getOrganisationById(1L)).thenReturn(organisation);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.assignOrganisation(1L, 1L);

        assertEquals(organisation, user.getOrganisation());
        verify(roleService, times(1)).getRoleByName("MODERATOR");
        verify(userRepository, times(2)).save(user);
    }

    @Test
    public void testAssignOrganisation_ModeratorNull() {
        Organisation organisation = new Organisation();
        organisation.setId(1L);

        User user = new User();
        user.setId(1L);

        Role role = new Role();
        role.setId(1L);
        role.setName("MODERATOR");
        user.setRoles(new ArrayList<>(List.of(role)));

        when(organisationService.getOrganisationById(1L)).thenReturn(organisation);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // When
        userService.assignOrganisation(1L, 1L);

        assertEquals(organisation, user.getOrganisation());
        assertTrue(user.getRoles().stream().anyMatch(roles -> roles.getName().equals("MODERATOR")));
        verify(roleService, times(1)).getRoleByName("MODERATOR");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testRevokeOrganisation() {
        Organisation organisation = new Organisation();
        organisation.setId(1L);

        User user = new User();
        user.setId(1L);
        user.setOrganisation(organisation);

        Role moderatorRole = new Role();
        moderatorRole.setId(1L);
        moderatorRole.setName("MODERATOR");
        user.setRoles(new ArrayList<>(List.of(moderatorRole)));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.revokeOrganisation(1L);

        assertFalse(user.getRoles().stream().anyMatch(role -> role.getName().equals("MODERATOR")));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testRefuse() {
        User user1 = new User();
        user1.setId(1L);

        Shift shift = new Shift();
        shift.setId(1L);

        ShiftToUser shiftToUser1 = new ShiftToUser(user1, shift, false);
        shift.setShiftToUsers(new ArrayList<>(List.of(shiftToUser1)));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(shiftService.getShiftById(1L)).thenReturn(shift);
        doNothing().when(shiftToUserRepository).delete(any());

        userService.refuse(1L, 1L);

        assertEquals(-1, shift.getRegisteredUsers());
        verify(shiftService, times(1)).editShift(shift);
    }

    @Test
    public void testRefuse_EventAlreadyPassed() {
        User user = new User();
        user.setId(1L);

        Shift shift = new Shift();
        shift.setId(1L);

        ShiftToUser shiftToUser1 = new ShiftToUser(user, shift, false);
        shift.setShiftToUsers(new ArrayList<>(List.of(shiftToUser1)));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(shiftService.getShiftById(1L)).thenReturn(shift);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.refuse(1L, 1L);
        });

        assertEquals("Can't refuse take part in event that has already taken place", exception.getMessage());
        verifyNoInteractions(shiftToUserRepository);
    }

    @Test
    public void testGetUsersByShift() {
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);

        List<User> users = new ArrayList<>(List.of(user1, user2));

        when(userRepository.findAllByShiftId(1L)).thenReturn(users);

        List<User> result = userService.getUsersByShift(1L);

        assertEquals(users.size(), result.size());
        assertEquals(users.get(0).getId(), result.get(0).getId());
        assertEquals(users.get(1).getId(), result.get(1).getId());

        verify(userRepository, times(1)).findAllByShiftId(1L);
    }
}
