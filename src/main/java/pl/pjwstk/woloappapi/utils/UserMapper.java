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

        default UserEntity toUser(UserRequestDto userRequestDto) {
            UserEntity user = new UserEntity();
            user.setFirstname(userRequestDto.getFirstname());
            user.setLastname(userRequestDto.getLastname());
            user.setEmail(userRequestDto.getEmail());
            user.setPhoneNumber(userRequestDto.getPhoneNumber());
            user.setPeselVerified(userRequestDto.isPeselVerified());
            user.setAgreementSigned(userRequestDto.isAgreementSigned());
            user.setAdult(userRequestDto.isAdult());
            user.setPassword(userRequestDto.getPassword());
            return user;
        }


}
