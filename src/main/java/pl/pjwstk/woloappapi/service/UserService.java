package pl.pjwstk.woloappapi.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.pjwstk.woloappapi.model.*;
import pl.pjwstk.woloappapi.model.entities.Organisation;
import pl.pjwstk.woloappapi.model.entities.Shift;
import pl.pjwstk.woloappapi.model.entities.ShiftToUser;
import pl.pjwstk.woloappapi.model.entities.User;
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
                if(userOptional.get().getRoles().stream().anyMatch(r ->
                        r.getName().equals(roleService.getRoleByName("ADMIN").getName()))){
                    List<User> admins = userRepository.findUsersByRole("ADMIN");
                    if(admins.size() == 1){
                        throw new IllegalArgumentException("User with ID " + userId
                        + "  is the only administrator of the application, to remove it, first create another administrator");
                    }
                }
                userRepository.deleteById(userId);
            }
        }


    @Transactional
    public void updateUser(UserRequestDto userDto, Long id) {
         User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException( "User with ID " + id + " does not exist"));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
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
        if(shift.getCapacity() > shift.getRegisteredUsers()) {
            shift.getShiftToUsers().add(new ShiftToUser(user, shift));
            shift.setRegisteredUsers(shift.getRegisteredUsers() + 1);
            shiftService.editShift(shift);
        }
        else{
            throw new IllegalArgumentException("The event is fully booked");
        }
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

    public List<User> getUsersByShift(Long shift) {
        return userRepository.findAllByShiftId(shift);
    }

    public User getCurrentUser(Authentication authentication) {
        var email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}
