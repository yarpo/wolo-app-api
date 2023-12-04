package pl.pjwstk.woloappapi.controller;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pl.pjwstk.woloappapi.model.Role;
import pl.pjwstk.woloappapi.service.RoleService;

import java.util.List;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @GetMapping()
    public ResponseEntity<List<Role>> getRoles() {
        List<Role> Roles = roleService.getAllRoles();
        return new ResponseEntity<>(Roles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        return new ResponseEntity<>(roleService.getRoleById(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addRole(@RequestBody Role Role) {
        roleService.createRole(Role);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<HttpStatus> editRole(
            @Valid @RequestBody Role role, @PathVariable Long id) {
        roleService.updateRole(role, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
