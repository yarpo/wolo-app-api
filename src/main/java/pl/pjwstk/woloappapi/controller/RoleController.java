package pl.pjwstk.woloappapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "Get all rols",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(
                                                    implementation = RoleDto.class))
                                    )
                            }
                    )
            }
    )
    @GetMapping()
    public ResponseEntity<List<RoleDto>> getRoles() {
        List<RoleDto> roleDtos = roleService.getAllRoles()
                .stream()
                .map(dictionariesMapper::toRoleDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(roleDtos, HttpStatus.OK);
    }

    @Operation(
            summary = "Get role by id",
            description = "Role must exist",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200" ,
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = RoleDto.class)
                                    )
                            }
                    )
            },
            parameters = {
                    @Parameter(name = "id",
                            description = "Role id",
                            example = "1"
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Long id) {
        RoleDto roleDto = dictionariesMapper.toRoleDto(roleService.getRoleById(id));
        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Adding new role",
            description = "id = null",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201"
                    )
            },
            parameters = {
                    @Parameter(name = "role",
                            description = "Role object to create",
                            schema = @Schema(implementation = RoleDto.class)
                    )
            }
    )
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addRole(@RequestBody RoleDto roleDto) {
        roleService.createRole(dictionariesMapper.toRole(roleDto).build());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete role",
            description = "all users who had deleted role will be assigned the role \"USER\"",
            responses = {
                    @ApiResponse(
                            description = "No content",
                            responseCode = "204"
                    )
            },
            parameters = {
                    @Parameter(name = "id",
                            description = "Role id",
                            example = "1"
                    )
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Edit role",
            description = "Role must exist",
            responses = {
                    @ApiResponse(
                            description = "No content",
                            responseCode = "204"
                    )
            },
            parameters = {
                    @Parameter(name = "role",
                            description = "Role object with changes",
                            schema = @Schema(implementation = RoleDto.class)
                    )
            }
    )
    @PutMapping("/edit")
    public ResponseEntity<HttpStatus> editRole(
            @Valid @RequestBody RoleDto roleDto) {
        roleService.updateRole(dictionariesMapper.toRole(roleDto).build());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
