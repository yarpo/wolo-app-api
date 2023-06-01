package pl.pjwstk.woloappapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.pjwstk.woloappapi.model.User;
import pl.pjwstk.woloappapi.repository.UserRepository;

@RestController
public class UserController {

    private pl.pjwstk.woloappapi.repository.UserRepository UserRepository;

    @Autowired
    public UserController(UserRepository UserRepository) {
        this.UserRepository = UserRepository;
    }

    @GetMapping("/User/all")
    Iterable<User> all() {
        return UserRepository.findAll();
    }

    @GetMapping("/User/{id}")
    User UserById(@PathVariable Long id) {
        return UserRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND));
    }

    @PostMapping("/User/save")
    User save(@RequestBody User User) {
        return UserRepository.save(User);
    }

}