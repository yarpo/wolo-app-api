package pl.pjwstk.woloappapi.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.pjwstk.woloappapi.model.UserResponseDto;
import pl.pjwstk.woloappapi.model.UserShortResponse;
import pl.pjwstk.woloappapi.model.entities.Role;
import pl.pjwstk.woloappapi.model.entities.User;
import pl.pjwstk.woloappapi.service.security.RegistrationRequest;

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

    public User.UserBuilder toUser(RegistrationRequest request) {
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .isAdult(request.getIsAdult())
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
