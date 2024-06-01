package pl.pjwstk.woloappapi.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import pl.pjwstk.woloappapi.model.entities.*;
import pl.pjwstk.woloappapi.repository.OrganisationRepository;
import pl.pjwstk.woloappapi.repository.ShiftToUserRepository;
import pl.pjwstk.woloappapi.repository.UserRepository;
import pl.pjwstk.woloappapi.utils.IllegalArgumentException;

import java.time.LocalDate;
import java.time.LocalTime;
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
    private OrganisationRepository organisationRepository;

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
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUserById(){
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User retrievedUser = userService.getUserById(1L);

        assertEquals(user.getId(), retrievedUser.getId());
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void testGetUsersOnlyWithUserRole() {
        var roleUser = Role.builder().name("USER").build();
        var roleModerator = Role.builder().name("MODERATOR").build();
        ArrayList<Role> list1 = new ArrayList<>();
        list1.add(roleUser);
        ArrayList<Role> list2 = new ArrayList<>();
        list2.add(roleModerator);
        list2.add(roleUser);
        var userWithUserRole = User.builder().roles(list1).build();
        var userWithModeratorRole = User.builder().roles(list2).build();
        List<User> users = List.of(userWithModeratorRole, userWithUserRole);
        when(userRepository.findUsersByRole("USER")).thenReturn(users);

        List<User> result = userService.getUsersOnlyWithUserRole();

        assertEquals(1, result.size());
        assertEquals(userWithUserRole, result.get(0));
        verify(userRepository, times(1)).findUsersByRole("USER");
    }

    @Test
    public void testDeleteUserWhenModeratorOfOrganisation() {
        var organisation = Organisation.builder()
                .id(1L)
                .name("Test Organisation")
                .build();
        var user =User.builder()
                .id(1L)
                .organisation(organisation)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                userService.deleteUser(1L));

        assertTrue(thrown.getMessage().contains("firstly assign new moderator to organisation"));

        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    public void testDeleteUserWhenOnlyAdmin() {
        var roleAdmin = Role.builder().name("ADMIN").build();
        var user = User.builder()
                .id(1L)
                .roles(List.of(roleAdmin))
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        when(roleService.getRoleByName("ADMIN")).thenReturn(roleAdmin);
        when(userRepository.findUsersByRole("ADMIN")).thenReturn(Collections.singletonList(user));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                userService.deleteUser(1L));

        assertTrue(thrown.getMessage().contains("to remove it, first create another administrator"));

        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    void testDeleteUser_UserNotModerator_NoAdmins() {
        var userId = 1L;
        var roleUser = Role.builder().name("USER").build();
        var roleAdmin = Role.builder().name("ADMIN").build();
        var user = User.builder()
                .id(userId)
                .roles(List.of(roleUser))
                .shifts(new ArrayList<>())
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(roleService.getRoleByName("ADMIN")).thenReturn(roleAdmin);

        assertDoesNotThrow(() -> userService.deleteUser(userId));

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

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
        var user = User.builder()
                .id(1L)
                .build();
        var shift = Shift.builder()
                .id(1L)
                .capacity(10)
                .registeredUsers(5)
                .shiftToUsers(new ArrayList<>())
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(shiftService.getShiftById(1L)).thenReturn(shift);

        userService.joinEvent(1L, 1L, false);

        assertEquals(6, shift.getRegisteredUsers());
        assertEquals(1, shift.getShiftToUsers().size());
        verify(shiftService, times(1)).editShift(shift);
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
        when(organisationRepository.save(any(Organisation.class))).thenReturn(organisation);

        userService.revokeOrganisation(1L);

        assertFalse(user.getRoles().stream().anyMatch(role -> role.getName().equals("MODERATOR")));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testRefuse() {
        var event = Event.builder()
                .date(LocalDate.now().plusDays(1))
                .build();
        var user = User.builder()
                .id(1L)
                .build();
        var shift = Shift.builder()
                .id(1L)
                .event(event)
                .build();
        var shiftToUser = new ShiftToUser(user, shift, false);
        shift.setShiftToUsers(new ArrayList<>(List.of(shiftToUser)));

        ShiftToUser shiftToUser1 = new ShiftToUser(user, shift, false);
        shift.setShiftToUsers(new ArrayList<>(List.of(shiftToUser1)));

        when(shiftService.getShiftById(1L)).thenReturn(shift);
        doNothing().when(shiftToUserRepository).delete(any());

        userService.refuse(1L, 1L);

        assertEquals(-1, shift.getRegisteredUsers());
        verify(shiftService, times(1)).editShift(shift);
    }

    @Test
    public void testRefuse_EventAlreadyPassed() {
        var event = Event.builder()
                .date(LocalDate.now().minusDays(1))
                .build();
        var user = User.builder()
                .id(1L)
                .build();
        var shift = Shift.builder()
                .id(1L)
                .event(event)
                .build();
        var shiftToUser = new ShiftToUser(user, shift, false);
        shift.setShiftToUsers(new ArrayList<>(List.of(shiftToUser)));

        ShiftToUser shiftToUser1 = new ShiftToUser(user, shift, false);
        shift.setShiftToUsers(new ArrayList<>(List.of(shiftToUser1)));

        when(shiftService.getShiftById(1L)).thenReturn(shift);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                userService.refuse(1L, 1L));

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

    @Test
    public void testGetCurrentUser_UserExists() {
        var user = User.builder()
                .email("test@example.com")
                .build();

        Authentication authentication = mock(Authentication.class);

        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        User result = userService.getCurrentUser(authentication);

        assertEquals("test@example.com", result.getEmail());
        verify(authentication, times(1)).getName();
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testCheckJoin_CollidingShifts() {
        var userId = 1L;
        var shiftId = 2L;
        var event = Event.builder()
                .id(1L)
                .date(LocalDate.now().plusDays(1))
                .build();
        var shift = Shift.builder()
                .id(shiftId)
                .event(event)
                .startTime(LocalTime.parse("09:00:00"))
                .endTime(LocalTime.parse("12:00:00"))
                .build();
        var existingEvent = Event.builder()
                .id(2L)
                .date(LocalDate.now().plusDays(1))
                .build();

        var existingShift = Shift.builder()
                .id(3L)
                .event(existingEvent)
                .startTime(LocalTime.parse("10:15:00"))
                .endTime(LocalTime.parse("14:15:00"))
                .build();
        var user = User.builder()
                .id(userId)
                .shifts(new ArrayList<>())
                .build();

        var shiftToUser = new ShiftToUser(user, existingShift, false);

        user.getShifts().add(shiftToUser);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(shiftService.getShiftById(shiftId)).thenReturn(shift);

        String result = userService.checkJoin(userId, shiftId);

        assertEquals("You have colliding shifts: 3", result);
    }

    @Test
    void testCheckJoin_Underage() {
        var userId = 1L;
        var shiftId = 2L;
        var shift = new Shift();
        shift.setId(shiftId);
        shift.setRequiredMinAge(18);
        shift.setEvent(new Event());

        var user = new User();
        user.setId(userId);
        user.setShifts(new ArrayList<>());
        user.setAdult(false);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(shiftService.getShiftById(shiftId)).thenReturn(shift);

        String result = userService.checkJoin(userId, shiftId);

        assertEquals("You can't join this shift because minimal required age is 18 and you are not adult", result);
    }

    @Test
    void testCheckJoin_PeselVerificationRequired() {
        var userId = 1L;
        var shiftId = 2L;
        var event = new Event();
        event.setPeselVerificationRequired(true);
        var shift = new Shift();
        shift.setId(shiftId);
        shift.setEvent(event);

        var user = new User();
        user.setId(userId);
        user.setShifts(new ArrayList<>());
        user.setPeselVerified(false);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(shiftService.getShiftById(shiftId)).thenReturn(shift);

        String result = userService.checkJoin(userId, shiftId);

        assertEquals("You can't join this shift because PESEL verification is required", result);
    }

    @Test
    void testCheckJoin_AgreementNeeded() {
        var userId = 1L;
        var shiftId = 2L;
        var event = new Event();
        event.setAgreementNeeded(true);
        Shift shift = new Shift();
        shift.setId(shiftId);
        shift.setEvent(event);

        var user = new User();
        user.setId(userId);
        user.setShifts(new ArrayList<>());
        user.setAgreementSigned(false);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(shiftService.getShiftById(shiftId)).thenReturn(shift);

        String result = userService.checkJoin(userId, shiftId);

        assertEquals("You can't join this shift because volunteers agreement is required", result);
    }

    @Test
    void testCheckJoin_EventAlreadyPassed() {
        var userId = 1L;
        var shiftId = 2L;
        var event = new Event();
        event.setDate(LocalDate.now().minusDays(1));
        var shift = new Shift();
        shift.setId(shiftId);
        shift.setEvent(event);

        var user = new User();
        user.setId(userId);
        user.setShifts(new ArrayList<>());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(shiftService.getShiftById(shiftId)).thenReturn(shift);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                userService.checkJoin(userId, shiftId));

        assertEquals("Can't join  event that has already taken place", exception.getMessage());
    }

    @Test
    void testCheckJoin_NoIssues() {
        var userId = 1L;
        var shiftId = 2L;
        var event = new Event();
        event.setDate(LocalDate.now().plusDays(1));
        var shift = new Shift();
        shift.setId(shiftId);
        shift.setEvent(event);

        var user = new User();
        user.setId(userId);
        user.setShifts(new ArrayList<>());
        user.setAdult(true);
        user.setPeselVerified(true);
        user.setAgreementSigned(true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(shiftService.getShiftById(shiftId)).thenReturn(shift);

        String result = userService.checkJoin(userId, shiftId);

        assertEquals("OK", result);
    }
}
