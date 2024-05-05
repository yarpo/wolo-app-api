package pl.pjwstk.woloappapi.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.pjwstk.woloappapi.model.UserRequestDto;
import pl.pjwstk.woloappapi.model.entities.Organisation;
import pl.pjwstk.woloappapi.model.entities.Shift;
import pl.pjwstk.woloappapi.model.entities.ShiftToUser;
import pl.pjwstk.woloappapi.model.entities.User;
import pl.pjwstk.woloappapi.repository.OrganisationRepository;
import pl.pjwstk.woloappapi.repository.ShiftToUserRepository;
import pl.pjwstk.woloappapi.repository.UserRepository;
import pl.pjwstk.woloappapi.utils.NotFoundException;
import pl.pjwstk.woloappapi.utils.IllegalArgumentException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ShiftToUserRepository shiftToUserRepository;
    private final RoleService roleService;
    private final OrganisationService organisationService;
    private final OrganisationRepository organisationRepository;
    private final ShiftService shiftService;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("User id not found!"));
    }

    public List<User> getUsersOnlyWithUserRole() {
        List<User> users = userRepository.findUsersByRole("USER");
        return users.stream()
                .filter(user -> user.getRoles().size() == 1)
                .toList();

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

    public String checkJoin(Long userId, Long shiftId){
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
        var shift = shiftService.getShiftById(shiftId);
        List<Long> collidingShifts = user.getShifts().stream()
                .map(ShiftToUser::getShift)
                .filter(existingShift -> existingShift.getEvent().getDate().isEqual(shift.getEvent().getDate()))
                .filter(existingShift -> existingShift.getStartTime().isBefore(shift.getEndTime()) &&
                        existingShift.getEndTime().isAfter(shift.getStartTime()) ||
                        existingShift.getStartTime().isBefore(shift.getStartTime()) &&
                        existingShift.getEndTime().isAfter(shift.getStartTime()) ||
                        existingShift.getStartTime().isAfter(shift.getStartTime()) &&
                        existingShift.getStartTime().isBefore(shift.getEndTime())
                        )
                .map(Shift::getId)
                .toList();
        if(!collidingShifts.isEmpty()) {
            return "You have colliding shifts: " + collidingShifts.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
        } else {
            return "OK";
        }
    }

    @Transactional
    public void joinEvent(Long userId, Long shiftId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
        var shift = shiftService.getShiftById(shiftId);
        if (shift.getEvent().getDate().isAfter(LocalDate.now())) {
            if (shift.getCapacity() > shift.getRegisteredUsers()) {
                var shiftToUser = shiftToUserRepository.save(new ShiftToUser(user, shift));
                shift.getShiftToUsers().add(shiftToUser);
                shift.setRegisteredUsers(shift.getRegisteredUsers() + 1);
                shiftService.editShift(shift);
            } else {
                throw new IllegalArgumentException("The event is fully booked");
            }
        }else{
            throw new IllegalArgumentException("Can't join  event that has already taken place");
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
        var organisation = user.getOrganisation();
        organisation.setApproved(false);
        organisationRepository.save(organisation);
        user.setOrganisation(null);
        user.getRoles().removeIf(r -> r.getName().equals("MODERATOR"));
        userRepository.save(user);

    }

    public void refuse(Long userId, Long shiftId) {
        var shift = shiftService.getShiftById(shiftId);
        if (shift.getEvent().getDate().isAfter(LocalDate.now())) {
            var userAssignedToShift = shift.getShiftToUsers().stream()
                    .anyMatch(stu -> stu.getUser().getId().equals(userId));
            if (userAssignedToShift) {
                var shiftToUser = shift.getShiftToUsers().stream()
                        .filter(stu -> stu.getUser().getId().equals(userId))
                        .findFirst()
                        .orElseThrow();

                shift.getShiftToUsers().remove(shiftToUser);
                shift.setRegisteredUsers(shift.getRegisteredUsers() - 1);
                shiftService.editShift(shift);
                shiftToUserRepository.delete(shiftToUser);
            }else {
                throw new IllegalArgumentException("This user is not assigned to shift with id " + shiftId);
            }
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
