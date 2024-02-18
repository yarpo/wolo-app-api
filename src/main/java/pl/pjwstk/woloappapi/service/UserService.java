package pl.pjwstk.woloappapi.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pjwstk.woloappapi.model.*;
import pl.pjwstk.woloappapi.repository.RoleRepository;
import pl.pjwstk.woloappapi.repository.ShiftRepository;
import pl.pjwstk.woloappapi.repository.ShiftToUserRepository;
import pl.pjwstk.woloappapi.repository.UserRepository;
import pl.pjwstk.woloappapi.utils.NotFoundException;
import pl.pjwstk.woloappapi.utils.UserMapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ShiftToUserRepository shiftToUserRepository;
    private final UserMapper userMapper;
    private final OrganisationService organisationService;
    private ShiftRepository shiftRepository;

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
        UserEntity user = userMapper.toUser(userDto).build();
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
                userRepository.deleteById(userId);   //??
            }
        }


    @Transactional
    public UserEntity updateUser(UserEntity newUser, Long id) {

         UserEntity user = userRepository.findById(id).orElseThrow(
                () ->
                        new IllegalArgumentException(
                                "User with ID " + id + " does not exist"));
        updateFieldIfDifferent(user::getFirstname, user::setFirstname, newUser.getFirstname());
        updateFieldIfDifferent(user::getLastname, user::setLastname, newUser.getLastname());
        updateFieldIfDifferent(user::getEmail, user::setEmail, newUser.getEmail());
        updateFieldIfDifferent(user::getPhoneNumber, user::setPhoneNumber, newUser.getPhoneNumber());
        updateFieldIfDifferent(user::isPeselVerified, user::setPeselVerified, newUser.isPeselVerified());
        updateFieldIfDifferent(user::isAgreementSigned, user::setAgreementSigned, newUser.isAgreementSigned());
        Role role = roleRepository.findById(newUser.getRole().getId()).orElse(user.getRole());
        updateFieldIfDifferent(user::getRole, user::setRole, role);
        updateFieldIfDifferent(user::isAdult, user::setAdult, newUser.isAdult());
        return userRepository.save(user);
    }

    public List<UserEntity> getByRoleId(Long roleId) {
        return userRepository.getUsersByRoleId(roleId);
    }

    public int getShiftsCountForUser(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElse(null);

        if (userEntity != null) {
            return userEntity.getShifts().size();
        } else {
            return 0;

        }
    }
    private <T> void updateFieldIfDifferent(
            Supplier<T> currentSupplier, Consumer<T> updateConsumer, T newValue) {
        if (!Objects.equals(currentSupplier.get(), newValue)) {
            updateConsumer.accept(newValue);

        }
    }

    @Transactional
    public void joinEvent(Long userId, Long shiftId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new EntityNotFoundException("Shift not found with id: " + shiftId));

        shift.getShiftToUsers().add(new ShiftToUser(user, shift));
        shiftRepository.save(shift);
    }
}
