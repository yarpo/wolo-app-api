package pl.pjwstk.woloappapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.Organisation;
import pl.pjwstk.woloappapi.model.Role;
import pl.pjwstk.woloappapi.model.User;
import pl.pjwstk.woloappapi.repository.UserRepository;
import pl.pjwstk.woloappapi.service.UserService;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    @Test
    public void testGetAllUsers(){
        List<User> users = new ArrayList<>();
        users.add(createValidUser(1L, "Jan", "Kowalski", "jkowal@gmail.com" ));
        users.add(createValidUser(1L, "Jakub", "Nowak", "jnow@gmail.com" ));
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(users, result);
        verify(userRepository, times(1)).findAll();

    }

    @Test
    public void testGetUserById_ExistingId() {
        Long userId = 1L;
        User user = createValidUser(userId, "Jan", "Kowalski", "jkowal@gmail.com" );
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getUserById(userId);

        assertEquals(user, result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetUserById_NonExistingId() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getUserById(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testCreateUser_ValidUser() {
        User user = createValidUser(1L, "Jan", "Kowalski", "jkowal@gmail.com" );

        userService.createUser(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testDeleteUser_ExistingId() {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void testDeleteUser_NonExistingId() {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> userService.deleteUser(userId));
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, never()).deleteById(userId);
    }

    private User createValidUser(Long id, String firstname, String lastname, String email){
        User user = new User();
        user.setId(id);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setPhoneNumber("123456789");
        user.setRole(new Role());
        user.setPeselVerified(true);
        user.setAgreementSigned(false);
        user.setAdult(true);
        user.setOrganization(new Organisation());
        return user;
    }
}
