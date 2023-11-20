package pl.pjwstk.woloappapi;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.pjwstk.woloappapi.controller.UserController;
import pl.pjwstk.woloappapi.model.User;
import pl.pjwstk.woloappapi.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testGetUsers() {
        List<User> users = new ArrayList<>();
        users.add(createUser(1L, "Jan", "Kowalski", "jkowal@gmail.com",
                "123456789", true, true, true));
        users.add(createUser(2L, "Jakub", "Nowak", "jnow@gmail.com",
                "987654321", true, false, false));
        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<User>> responseEntity = userController.getUsers();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(users, responseEntity.getBody());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    public void testGetUserById_ExistingId() {
        Long userId = 1L;
        User user = createUser(userId, "Jan", "Kowalski", "jkowal@gmail.com",
                "123456789", true, true, true);
        when(userService.getUserById(userId)).thenReturn(user);

        ResponseEntity<User> responseEntity = userController.getUserById(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    public void testAddUser_ValidUser() {
        User userToAdd = createUser(1L, "Jan", "Kowalski", "jkowal@gmail.com",
                "123456789", true, true, true);

        ResponseEntity<HttpStatus> responseEntity = userController.addUser(userToAdd);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(userService, times(1)).createUser(userToAdd);
    }


    private User createUser(Long id, String firstname, String lastname, String email,
                            String phoneNumber, boolean isPeselVerified,
                            boolean isAgreementSigned, boolean isAdult) {
        User user = new User();
        user.setId(id);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setPeselVerified(isPeselVerified);
        user.setAgreementSigned(isAgreementSigned);
        user.setAdult(isAdult);
        return user;
    }


}
