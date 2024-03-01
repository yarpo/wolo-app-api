package pl.pjwstk.woloappapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjwstk.woloappapi.model.RoleDto;
import pl.pjwstk.woloappapi.service.RoleService;
import pl.pjwstk.woloappapi.utils.DictionariesMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/roles")
@Tag(name = "Roles")
public class RoleController {

    private final RoleService roleService;
    private final DictionariesMapper dictionariesMapper;

    @GetMapping()
    public ResponseEntity<List<RoleDto>> getRoles() {
        List<RoleDto> roleDtos = roleService.getAllRoles()
                .stream()
                .map(dictionariesMapper::toRoleDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(roleDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Long id) {
        RoleDto roleDto = dictionariesMapper.toRoleDto(roleService.getRoleById(id));
        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addRole(@RequestBody RoleDto roleDto) {
        roleService.createRole(dictionariesMapper.toRole(roleDto).build());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/edit")
    public ResponseEntity<HttpStatus> editRole(
            @Valid @RequestBody RoleDto roleDto) {
        roleService.updateRole(dictionariesMapper.toRole(roleDto).build());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
