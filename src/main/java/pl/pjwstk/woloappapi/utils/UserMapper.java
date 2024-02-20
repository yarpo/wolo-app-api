package pl.pjwstk.woloappapi.utils;

import org.mapstruct.Mapper;
import pl.pjwstk.woloappapi.model.UserEntity;
import pl.pjwstk.woloappapi.model.UserRequestDto;
import pl.pjwstk.woloappapi.model.UserResponseDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    default UserResponseDto toUserResponseDto(UserEntity user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setFirstname(user.getFirstname());
        userResponseDto.setLastname(user.getLastname());
        userResponseDto.setAdult(user.isAdult());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setPhoneNumber(user.getPhoneNumber());
        userResponseDto.setAgreementSigned(user.isAgreementSigned());
        userResponseDto.setPeselVerified(user.isPeselVerified());
        userResponseDto.setRole(user.getRole().getName());
        return userResponseDto;
    }
    default UserEntity.UserEntityBuilder toUser(UserRequestDto userRequestDto) {
        return UserEntity.builder()
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
