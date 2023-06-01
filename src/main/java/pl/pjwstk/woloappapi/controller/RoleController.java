package pl.pjwstk.woloappapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.pjwstk.woloappapi.model.Role;
import pl.pjwstk.woloappapi.repository.RoleRepository;

@RestController
public class RoleController  {

    private RoleRepository RoleRepository;

    @Autowired
    public RoleController(RoleRepository RoleRepository) {
        this.RoleRepository = RoleRepository;
    }

    @GetMapping("/Role/all")
    Iterable<Role> all() {
        return RoleRepository.findAll();
    }

    @GetMapping("/Role/{id}")
    Role RoleById(@PathVariable Long id) {
        return RoleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND));
    }

    @PostMapping("/Role/save")
    Role save(@RequestBody Role Role) {
        return RoleRepository.save(Role);
    }

}