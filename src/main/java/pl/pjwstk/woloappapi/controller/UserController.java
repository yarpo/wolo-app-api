package pl.pjwstk.woloappapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjwstk.woloappapi.model.*;
import pl.pjwstk.woloappapi.service.EventService;
import pl.pjwstk.woloappapi.service.UserService;
import pl.pjwstk.woloappapi.utils.EventMapper;
import pl.pjwstk.woloappapi.utils.UserMapper;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@Tag(name = "Users")
public class UserController {

    private final UserService userService;
    private final EventService eventService;
    private final UserMapper userMapper;
    private final EventMapper eventMapper;

    @Operation(
            summary = "Get all users",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(type = "array",implementation = UserResponseDto.class)
                                    )
                            }
                    )
            }
    )
    @GetMapping()
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        List<UserEntity> users = userService.getAllUsers();
        List<UserResponseDto> userResponseDtos = users.stream()
                .map(userMapper::toUserResponseDto)
                .toList();
        return new ResponseEntity<>(userResponseDtos, HttpStatus.OK);

    }

    @Operation(
            summary = "Get user by id",
            description = "User must exist",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200" ,
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = UserResponseDto.class)
                                    )
                            }
                    )
            },
            parameters = {
                    @Parameter(name = "id",
                            description = "User id",
                            example = "1"
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        UserEntity user = userService.getUserById(id);
        UserResponseDto userResponseDto= userMapper.toUserResponseDto(user);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Get all user events",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(type = "array",implementation = EventResponseDto.class)
                                    )
                            }
                    )
            },
            parameters = {
                    @Parameter(name = "id",
                            description = "User id",
                            example = "1"
                    )
            }
    )
    @GetMapping("/{id}/events")
    public ResponseEntity<List<EventResponseDto>>getUserEvents(@PathVariable Long id){
        List<Event> events = eventService.getEventsByUser(id);
        List<EventResponseDto> requests = events.stream()
                .map(eventMapper::toEventResponseDto).toList();
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete user",
            description = "If the user has the moderator role, it cannot be deleted",
            responses = {
                    @ApiResponse(
                            description = "No content",
                            responseCode = "204"
                    )
            },
            parameters = {
                    @Parameter(name = "id",
                            description = "User id",
                            example = "1"
                    )
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Edit user",
            description = "User must exist",
            responses = {
                    @ApiResponse(
                            description = "No content",
                            responseCode = "204"
                    )
            },
            parameters = {
                    @Parameter(name = "user",
                            description = "User object with changes",
                            schema = @Schema(implementation = UserRequestDto.class)
                    ),
                    @Parameter(name = "id",
                            description = "User id",
                            example = "1"
                    )
            }
    )
    @PutMapping("/{id}/edit")
    public ResponseEntity<HttpStatus> editUser(@Valid @RequestBody UserRequestDto userRequestDto,
                                           @PathVariable Long id) {
        userService.updateUser(userRequestDto, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @Operation(
            summary = "Make user moderator of organisation",
            description = "User and organisation must exist",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    )
            },
            parameters = {
                    @Parameter(name = "user",
                            description = "User id",
                            example = "1"
                    ),
                    @Parameter(name = "organisation",
                            description = "Organisation id",
                            example = "1"
                    )
            }
    )
    @PostMapping("/assign")
    public ResponseEntity<HttpStatus> assignOrganisation(@RequestParam(value = "user") Long userId,
                                                         @RequestParam(value = "organisation") Long organisationId){
        userService.assignOrganisation(userId, organisationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Revoke the user from the role of moderator of the organization",
            description = "User and organisation must exist",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    )
            },
            parameters = {
                    @Parameter(name = "user",
                            description = "User id",
                            example = "1"
                    )
            }
    )
    @PostMapping("/revoke")
    public ResponseEntity<HttpStatus> revokeOrganisation(@RequestParam(value = "user") Long userId){
        userService.revokeOrganisation(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
