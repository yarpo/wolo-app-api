package pl.pjwstk.woloappapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.pjwstk.woloappapi.model.Role;
import pl.pjwstk.woloappapi.repository.RoleRepository;

@RestController
@AllArgsConstructor
@RequestMapping("/role")
public class RoleController  {

    private final RoleRepository RoleRepository;

    @GetMapping("/all")
    Iterable<Role> all() {
        return RoleRepository.findAll();
    }

    @GetMapping("/{id}")
    Role RoleById(@PathVariable Long id) {
        return RoleRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/Role/save")
    Role save(@RequestBody Role Role) {
        return RoleRepository.save(Role);
    }

}
