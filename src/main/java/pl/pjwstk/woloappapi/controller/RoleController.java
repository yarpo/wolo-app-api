package pl.pjwstk.woloappapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjwstk.woloappapi.model.Role;
import pl.pjwstk.woloappapi.model.RoleDto;
import pl.pjwstk.woloappapi.service.RoleService;
import pl.pjwstk.woloappapi.utils.UserMapper;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;
    private final UserMapper userMapper;

    @GetMapping()
    public ResponseEntity<List<RoleDto>> getRoles() {
        List<Role> Roles = roleService.getAllRoles();
        List<RoleDto> roleDtos = Roles.stream()
                .map(userMapper::toRoleDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(roleDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Long id) {
        Role role = roleService.getRoleById(id);
        RoleDto roleDto = userMapper.toRoleDto(role);
        return new ResponseEntity<>(roleDto, HttpStatus.OK);
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
