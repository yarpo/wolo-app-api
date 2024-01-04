package pl.pjwstk.woloappapi.utils;

import org.mapstruct.Mapper;
import pl.pjwstk.woloappapi.model.*;

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
            userResponseDto.setRoleDto(toRoleDto(user.getRole()));
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
            user.setRole(toRole(userRequestDto.getRoleDto()));
            return user;
        }
    default RoleDto toRoleDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());
        return roleDto;
    }

    default Role toRole(RoleDto roleDto) {
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setName(roleDto.getName());
        return role;
    }

}
