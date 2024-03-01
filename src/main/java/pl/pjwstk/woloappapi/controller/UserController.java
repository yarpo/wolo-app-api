package pl.pjwstk.woloappapi.controller;

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

    @GetMapping()
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        List<UserEntity> users = userService.getAllUsers();
        List<UserResponseDto> userResponseDtos = users.stream()
                .map(userMapper::toUserResponseDto)
                .toList();
        return new ResponseEntity<>(userResponseDtos, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        UserEntity user = userService.getUserById(id);
        UserResponseDto userResponseDto= userMapper.toUserResponseDto(user);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{id}/events")
    public ResponseEntity<List<EventResponseDto>>getUserEvents(@PathVariable Long id){
        List<Event> events = eventService.getEventsByUser(id);
        List<EventResponseDto> requests = events.stream()
                .map(eventMapper::toEventResponseDto).toList();
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{id}/edit")
    public ResponseEntity<HttpStatus> editUser(@Valid @RequestBody UserRequestDto userRequestDto,
                                           @PathVariable Long id) {
        userService.updateUser(userRequestDto, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
    @PostMapping("/assign")
    public ResponseEntity<HttpStatus> assignOrganisation(@RequestParam(value = "user") Long userId,
                                                         @RequestParam(value = "organisation") Long organisationId){
        userService.assignOrganisation(userId, organisationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/revoke")
    public ResponseEntity<HttpStatus> revokeOrganisation(@RequestParam(value = "user") Long userId){
        userService.revokeOrganisation(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
