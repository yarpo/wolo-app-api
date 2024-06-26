package pl.pjwstk.woloappapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.pjwstk.woloappapi.model.*;
import pl.pjwstk.woloappapi.model.entities.Event;
import pl.pjwstk.woloappapi.model.entities.ShiftToUser;
import pl.pjwstk.woloappapi.model.entities.User;
import pl.pjwstk.woloappapi.repository.ShiftToUserRepository;
import pl.pjwstk.woloappapi.service.EventService;
import pl.pjwstk.woloappapi.service.ShiftService;
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
    private final ShiftService shiftService;
    private final UserMapper userMapper;
    private final EventMapper eventMapper;
    private final ShiftToUserRepository shiftToUserRepository;

    @GetMapping()
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        List<User> users = userService.getAllUsers();
        List<UserResponseDto> userResponseDtos = users.stream()
                .map(userMapper::toUserResponseDto)
                .toList();
        return new ResponseEntity<>(userResponseDtos, HttpStatus.OK);

    }

    @GetMapping("/volunteers")
    public ResponseEntity<List<UserShortResponse>> getUserShorts() {
        List<User> users = userService.getUsersOnlyWithUserRole();
        List<UserShortResponse> userShortResponses = users.stream()
                .map(userMapper::toUserShortRespons)
                .toList();
        return new ResponseEntity<>(userShortResponses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserResponseDto userResponseDto= userMapper.toUserResponseDto(user);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<List<EventResponseDto>>getUserEvents(@PathVariable Long id){
        List<Event> events = eventService.getEventsByUser(id);
        List<EventResponseDto> requests = events.stream()
                .map(eventMapper::toEventResponseDto).toList();
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping("/shifts/past")
    public ResponseEntity<List<ShiftResponseDto>> getUserPastSifts() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = userService.getCurrentUser(authentication).getId();
        List<ShiftToUser> shiftToUsers = shiftService.getPastEventsByUser(userId);
        List<ShiftResponseDto> shifts = shiftToUsers.stream()
                .map(stu -> eventMapper.toShiftResponseDto(stu.getShift()))
                .toList();
        return new ResponseEntity<>(shifts, HttpStatus.OK);
    }

    @GetMapping("/shifts/current")
    public ResponseEntity<List<ShiftResponseDto>> getUserCurrentShifts() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = userService.getCurrentUser(authentication).getId();
        List<ShiftToUser> shiftToUsers = shiftService.getCurrentEventsByUser(userId);
        List<ShiftResponseDto> shifts = shiftToUsers.stream()
                .map(stu -> eventMapper.toShiftResponseDto(stu.getShift()))
                .toList();
        return new ResponseEntity<>(shifts, HttpStatus.OK);
    }

    @GetMapping("/shifts/reserve")
    public ResponseEntity<List<ShiftResponseDto>> getUserReserveShifts() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = userService.getCurrentUser(authentication).getId();
        List<ShiftToUser> shiftToUsers = shiftService.getReserveEventsByUser(userId);
        List<ShiftResponseDto> shifts = shiftToUsers.stream()
                .map(stu -> eventMapper.toShiftResponseDto(stu.getShift()))
                .toList();
        return new ResponseEntity<>(shifts, HttpStatus.OK);
    }

    @GetMapping("/{id}/shifts")
    public ResponseEntity<List<ShiftResponseDto>> getUserShifts(@PathVariable Long id){
        List<ShiftToUser> shiftToUsers = shiftToUserRepository.findShiftToUsersByUserId(id);
        List<ShiftResponseDto> shifts = shiftToUsers.stream()
                .map(stu -> eventMapper.toShiftResponseDto(stu.getShift()))
                .toList();
        return new ResponseEntity<>(shifts, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<HttpStatus> editUser(@Valid @RequestBody UserEditRequestAdminDto userEditRequestAdminDto,
                                           @PathVariable Long id) {
        userService.updateUserByAdmin(userEditRequestAdminDto, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping("/edit")
    public ResponseEntity<HttpStatus> editUser(@Valid @RequestBody UserEditRequestDto userEditRequestDto) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = userService.getCurrentUser(authentication).getId();
        userService.updateUser(userEditRequestDto, userId);
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

    @PostMapping("/changerole")
    public ResponseEntity<HttpStatus> changeRole(@RequestParam(value = "user") Long userId,
                                                         @RequestParam(value = "roles") List<Long> rolesToUpdate){
        userService.updateUserRoles(userId, rolesToUpdate);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
