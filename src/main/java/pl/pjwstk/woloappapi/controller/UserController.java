package pl.pjwstk.woloappapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pjwstk.woloappapi.model.User;
import pl.pjwstk.woloappapi.service.UserService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getEvents(){
        List<User> users = userService.getAllEvents();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable Long role){
        return new ResponseEntity<>(userService.getByRole(role), HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addUser(@RequestBody User user){
        userService.createUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
