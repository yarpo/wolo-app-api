package pl.pjwstk.woloappapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjwstk.woloappapi.model.User;
import pl.pjwstk.woloappapi.service.UserService;

import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<User>> getUsers(){
        List<User> Users = userService.getAllUsers();
        return new ResponseEntity<>(Users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Map<String, Object>>> getUserById(@PathVariable Long id){
        User user = userService.getUserById(id);
        if (user != null) {
            List<Map<String, Object>> userMapList = new ArrayList<>();
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("id", user.getId());
            userMap.put("firstname", user.getFirstname());
            userMap.put("lastname", user.getLastname());
            userMap.put("email", user.getEmail());
            userMap.put("phoneNumber", user.getPhoneNumber());
            userMap.put("isPeselVerified", user.isPeselVerified());
            userMap.put("isAgreementSigned", user.isAgreementSigned());
            userMap.put("isAdult", user.isAdult());
            if (user.getRole() != null) {
                userMap.put("roleName", user.getRole().getName());
            }


            if (user.getOrganization() != null) {
                userMap.put("organizationName", user.getOrganization().getName());
            }
            userMapList.add(userMap);

            return new ResponseEntity<>(userMapList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addUser(@RequestBody User User){
        userService.createUser(User);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}