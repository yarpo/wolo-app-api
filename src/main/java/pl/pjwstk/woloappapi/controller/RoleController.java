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
}
