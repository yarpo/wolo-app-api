package pl.pjwstk.woloappapi.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.Organisation;
import pl.pjwstk.woloappapi.model.Role;
import pl.pjwstk.woloappapi.model.User;
import pl.pjwstk.woloappapi.model.UserRequestDto;
import pl.pjwstk.woloappapi.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private RoleService roleService;

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

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleService.getRoleByName("ADMIN")).thenReturn(adminRole);
        when(userRepository.findUsersByRole("ADMIN")).thenReturn(new ArrayList<>());

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

    @Test
    public void testUpdateUser() {
        UserRequestDto userDto = new UserRequestDto();
        userDto.setFirstname("Test Firstname");
        userDto.setLastname("Test Lastname");
        userDto.setEmail("test@example.com");
        userDto.setPhoneNumber("123456789");
        userDto.setPeselVerified(true);
        userDto.setAgreementSigned(true);
        userDto.setAdult(true);

        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.updateUser(userDto, 1L);

        verify(userRepository, times(1)).findById(1L);
        assertEquals(userDto.getFirstname(), user.getFirstname());
        assertEquals(userDto.getLastname(), user.getLastname());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getPhoneNumber(), user.getPhoneNumber());
        assertEquals(userDto.isPeselVerified(), user.isPeselVerified());
        assertEquals(userDto.isAgreementSigned(), user.isAgreementSigned());
        assertEquals(userDto.isAdult(), user.isAdult());
    }
    
}
