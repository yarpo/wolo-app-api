package pl.pjwstk.woloappapi.utils;

import org.springframework.stereotype.Component;
import pl.pjwstk.woloappapi.model.Role;
import pl.pjwstk.woloappapi.model.User;
import pl.pjwstk.woloappapi.model.UserResponseDto;
import pl.pjwstk.woloappapi.security.RegistrationRequest;

@Component
public class UserMapper {
    public UserResponseDto toUserResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setFirstname(user.getFirstname());
        userResponseDto.setLastname(user.getLastname());
        userResponseDto.setAdult(user.isAdult());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setPhoneNumber(user.getPhoneNumber());
        userResponseDto.setAgreementSigned(user.isAgreementSigned());
        userResponseDto.setPeselVerified(user.isPeselVerified());
        userResponseDto.setRoles(user.getRoles().stream().map(Role::getName).toList());
        return userResponseDto;
    }
    public User.UserBuilder toUser(RegistrationRequest request) {
        return User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .isAdult(request.isAdult())
                .isPeselVerified(false)
                .isAgreementSigned(false);
    }
}
