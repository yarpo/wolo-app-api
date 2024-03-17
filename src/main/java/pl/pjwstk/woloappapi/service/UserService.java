package pl.pjwstk.woloappapi.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pjwstk.woloappapi.model.*;
import pl.pjwstk.woloappapi.repository.ShiftToUserRepository;
import pl.pjwstk.woloappapi.repository.UserRepository;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ShiftToUserRepository shiftToUserRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;
    private final OrganisationService organisationService;
    private final ShiftService shiftService;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("User id not found!"));
    }
    @Transactional
        public void deleteUser(Long userId) {
            Optional<User> userOptional = userRepository.findById(userId);
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
         User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException( "User with ID " + id + " does not exist"));
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPeselVerified(userDto.isPeselVerified());
        user.setAgreementSigned(userDto.isAgreementSigned());
        user.setAdult(userDto.isAdult());
        userRepository.save(user);
    }

    @Transactional
    public void updateUserRoles(Long userId, List<Long> rolesToUpdate) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException( "User with ID " + userId + " does not exist"));

        user.getRoles().removeIf(rtu ->!rolesToUpdate.contains(rtu.getId()));

        rolesToUpdate.stream()
                .filter(roleId ->user.getRoles()
                        .stream()
                        .noneMatch(rtu ->
                                roleId.equals(rtu.getId())))
                .map(roleService::getRoleById)
                .forEach(user.getRoles()::add);
    }

    @Transactional
    public void joinEvent(Long userId, Long shiftId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
        Shift shift = shiftService.getShiftById(shiftId);

        ShiftToUser shiftToUser = shiftToUserRepository.save(new ShiftToUser(user, shift));
        shift.getShiftToUsers().add(shiftToUser);
        shift.setRegisteredUsers(shift.getRegisteredUsers() + 1);
        shiftService.editShift(shift);
    }

    @Transactional
    public void assignOrganisation(Long userId, Long organisationId) {
        Organisation organisation = organisationService.getOrganisationById(organisationId);
        User moderator = organisation.getModerator();
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User not found with id: " + userId));
        if(moderator == null){
            user.setOrganisation(organisation);
        }else{
            moderator.setOrganisation(null);
            moderator.getRoles().removeIf(r -> r.getName().equals("MODERATOR"));
            userRepository.save(moderator);
            user.setOrganisation(organisation);
        }
        user.getRoles().add(roleService.getRoleByName("MODERATOR"));
        userRepository.save(user);
    }

    @Transactional
    public void revokeOrganisation(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User not found with id: " + userId));
        user.setOrganisation(null);
        user.getRoles().removeIf(r -> r.getName().equals("MODERATOR"));
        userRepository.save(user);
    }

    public void refuse(Long userId, Long shiftId) {
        User user = userRepository.findById(userId)
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
        }else{
            throw new IllegalArgumentException("Can't refuse take part in event that has already taken place");
        }
    }
}
