package pl.pjwstk.woloappapi.utils;

import org.mapstruct.Mapper;
import pl.pjwstk.woloappapi.model.Role;
import pl.pjwstk.woloappapi.model.User;
import pl.pjwstk.woloappapi.model.UserRequestDto;
import pl.pjwstk.woloappapi.model.UserResponseDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    default UserResponseDto toUserResponseDto(User user) {
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
    default User.UserBuilder toUser(UserRequestDto userRequestDto) {
        return User.builder()
                .firstname(userRequestDto.getFirstname())
                .lastname(userRequestDto.getLastname())
                .email(userRequestDto.getEmail())
                .phoneNumber(userRequestDto.getPhoneNumber())
                .isPeselVerified(userRequestDto.isPeselVerified())
                .isAgreementSigned(userRequestDto.isAgreementSigned())
                .isAdult(userRequestDto.isAdult())
                .password(userRequestDto.getPassword());
    }

}
