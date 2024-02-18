package pl.pjwstk.woloappapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjwstk.woloappapi.model.*;
import pl.pjwstk.woloappapi.service.EventService;
import pl.pjwstk.woloappapi.service.UserService;
import pl.pjwstk.woloappapi.utils.EventMapper;
import pl.pjwstk.woloappapi.utils.UserMapper;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
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
                .collect(Collectors.toList());
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

    //to do wyrzucenia
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addUser(@RequestBody UserRequestDto userRequestDto) {
        userService.createUser(userRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{id}/edit")
    public ResponseEntity<Object> editUser(
            @Valid @RequestBody UserRequestDto userRequestDto, @PathVariable Long id) {
        try {
            UserEntity updatedUser = userService.updateUser(userMapper.toUser(userRequestDto).build(), id);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
