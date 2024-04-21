package pl.pjwstk.woloappapi.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.pjwstk.woloappapi.model.ShiftResponseDto;
import pl.pjwstk.woloappapi.model.UserResponseDto;
import pl.pjwstk.woloappapi.model.UserShortResponse;
import pl.pjwstk.woloappapi.model.entities.*;
import pl.pjwstk.woloappapi.security.RegistrationRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final EventMapper eventMapper;
    public UserResponseDto toUserResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setPhoneNumber(user.getPhoneNumber());
        userResponseDto.setPeselVerified(user.isPeselVerified());
        userResponseDto.setAgreementSigned(user.isAgreementSigned());
        userResponseDto.setAdult(user.isAdult());
        userResponseDto.setRoles(user.getRoles().stream().map(Role::getName).toList());
        userResponseDto.setOrganisationId(user.getOrganisation() != null ? user.getOrganisation().getId() : null);
        userResponseDto.setOrganisationName(user.getOrganisation() != null ? user.getOrganisation().getName() : null);
        var shifts = user.getShifts()
                .stream()
                .map(stu -> eventMapper.toShiftInfoRespons(stu.getShift()))
                .collect(Collectors.toList());
        userResponseDto.setShifts(shifts);
        return userResponseDto;
    }

    public ShiftResponseDto toShiftResponseDto(ShiftToUser shiftToUser) {
        ShiftResponseDto shiftResponseDto = new ShiftResponseDto();
        shiftResponseDto.setShiftId(shiftToUser.getShift().getId());
        shiftResponseDto.setEventId(shiftToUser.getShift().getEvent().getId());
        shiftResponseDto.setEventName(shiftToUser.getShift().getEvent().getName());
        shiftResponseDto.setStartTime(shiftToUser.getShift().getStartTime());
        shiftResponseDto.setEndTime(shiftToUser.getShift().getEndTime());
        shiftResponseDto.setDate(shiftToUser.getShift().getDate());
        shiftResponseDto.setLeaderRequired(shiftToUser.getShift().isLeaderRequired());
        shiftResponseDto.setRequiredMinAge(shiftToUser.getShift().getRequiredMinAge());
        shiftResponseDto.setRegisteredUsers(shiftToUser.getShift().getRegisteredUsers());
        shiftResponseDto.setDistrict(shiftToUser.getShift().getAddress().getDistrict().getName());
        shiftResponseDto.setStreet(shiftToUser.getShift().getAddress().getStreet());
        shiftResponseDto.setHomeNum(shiftToUser.getShift().getAddress().getHomeNum());
        shiftResponseDto.setShiftDirections(shiftToUser.getShift().getShiftDirections());
        return shiftResponseDto;
    }

    public User.UserBuilder toUser(RegistrationRequest request) {
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .isAdult(request.isAdult())
                .isPeselVerified(false)
                .isAgreementSigned(false);
    }

    public UserShortResponse toUserShortRespons(User user) {
        return UserShortResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
