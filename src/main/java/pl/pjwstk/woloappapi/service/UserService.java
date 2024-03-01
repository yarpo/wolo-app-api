package pl.pjwstk.woloappapi.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pjwstk.woloappapi.model.*;
import pl.pjwstk.woloappapi.repository.ShiftToUserRepository;
import pl.pjwstk.woloappapi.repository.UserRepository;
import pl.pjwstk.woloappapi.utils.NotFoundException;
import pl.pjwstk.woloappapi.utils.UserMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ShiftToUserRepository shiftToUserRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;
    private final OrganisationService organisationService;
    private ShiftService shiftService;

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("User id not found!"));
    }
    @Transactional
    public void createUser(UserRequestDto userDto) {
        UserEntity user = userMapper.toUser(userDto)
                .role(roleService.getRoleByName("USER"))
                .build();
        userRepository.save(user);
    }

    @Transactional
        public void deleteUser(Long userId) {
            Optional<UserEntity> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                Organisation organisation = userOptional.get().getOrganisation();
                if(organisation != null){
                    throw new IllegalArgumentException("User with ID " + userId
                            + " is moderator of "+ organisation.getName()
                            + "firstly assign new moderator to organisation");
                }
                userRepository.deleteById(userId);
            }
        }


    @Transactional
    public void updateUser(UserRequestDto userDto, Long id) {
         UserEntity user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException( "User with ID " + id + " does not exist"));
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPeselVerified(userDto.isPeselVerified());
        user.setAgreementSigned(userDto.isAgreementSigned());
        user.setAdult(userDto.isAdult());
        Role role = roleService.getRoleById(userDto.getRoleId());
        user.setRole(role);
        userRepository.save(user);
    }
    
    @Transactional
    public void joinEvent(Long userId, Long shiftId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
        Shift shift = shiftService.getShiftById(shiftId);
        shift.getShiftToUsers().add(new ShiftToUser(user, shift));
        shift.setRegisteredUsers(shift.getRegisteredUsers() + 1);
        shiftService.editShift(shift);
    }

    @Transactional
    public void assignOrganisation(Long userId, Long organisationId) {
        Organisation organisation = organisationService.getOrganisationById(organisationId);
        UserEntity moderator = organisation.getModerator();
        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User not found with id: " + userId));
        if(moderator == null){
            user.setOrganisation(organisation);
        }else{
            moderator.setOrganisation(null);
            moderator.setRole(roleService.getRoleByName("USER"));
            userRepository.save(moderator);
            user.setOrganisation(organisation);
        }
        user.setRole(roleService.getRoleByName("MODERATOR"));
        userRepository.save(user);
    }

    @Transactional
    public void revokeOrganisation(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User not found with id: " + userId));
        user.setOrganisation(null);
        user.setRole(roleService.getRoleByName("USER"));
        userRepository.save(user);
    }

    public void refuse(Long userId, Long shiftId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
        Shift shift = shiftService.getShiftById(shiftId);
        if (shift.getDate().isAfter(LocalDate.now())) {
            var shiftToUser = shift.getShiftToUsers()
                    .stream()
                    .filter(stu -> stu.getUser().equals(user))
                    .findFirst();
            shiftToUser.ifPresent(shiftToUserRepository::delete);
            shift.setRegisteredUsers(shift.getRegisteredUsers() - 1);
            shiftService.editShift(shift);
        }
    }
}
