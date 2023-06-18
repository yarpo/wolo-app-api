package pl.pjwstk.woloappapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjwstk.woloappapi.model.User;
import pl.pjwstk.woloappapi.service.UserService;
import pl.pjwstk.woloappapi.utils.UserNotFoundException;

import java.util.List;
import java.util.Optional;

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
        Optional<User> optionalUser = userService.getUserById(id);
        if (optionalUser.isPresent()){
            return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
        } else {
            throw new UserNotFoundException("User id not found!");
        }
    }

    @GetMapping("/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable Long role){
        return new ResponseEntity<>(userService.getByRole(role), HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addUser(@RequestBody User user){
        userService.createUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}