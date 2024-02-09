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
        UserEntity user = userMapper.toUser(userDto);
        userRepository.save(user);
    }

    @Transactional
        public void deleteUser(Long userId) {
            Optional<UserEntity> userOptional = userRepository.findById(userId);

            if (userOptional.isPresent()) {
                List<Organisation>userOrganisations = organisationService.findOrganisationsByModeratorId(userId);
                if(!userOrganisations.isEmpty()){
                    organisationService.removeModeratorFromOrganisations(userOrganisations,userId);
                }

                UserEntity user = userOptional.get();
                shiftToUserRepository.deleteByUser(user);
                userRepository.deleteById(userId);
            } else {
                throw new IllegalArgumentException("User with ID " + userId + " does not exist");
            }
        }


    @Transactional
    public UserEntity updateUser(UserRequestDto userRequestDto, Long id) {

         UserEntity user = userRepository.findById(id).orElseThrow(
                () ->
                        new IllegalArgumentException(
                                "User with ID " + id + " does not exist"));
        updateFieldIfDifferent(user::getFirstname, user::setFirstname, userRequestDto.getFirstname());
        updateFieldIfDifferent(user::getLastname, user::setLastname, userRequestDto.getLastname());
        updateFieldIfDifferent(user::getEmail, user::setEmail, userRequestDto.getEmail());
        updateFieldIfDifferent(user::getPhoneNumber, user::setPhoneNumber, userRequestDto.getPhoneNumber());
        updateFieldIfDifferent(user::isPeselVerified, user::setPeselVerified, userRequestDto.isPeselVerified());
        updateFieldIfDifferent(user::isAgreementSigned, user::setAgreementSigned, userRequestDto.isAgreementSigned());
        Role role = roleRepository.findById(userRequestDto.getRoleDto().getId()).orElse(user.getRole());
        updateFieldIfDifferent(user::getRole, user::setRole, role);
        updateFieldIfDifferent(user::isAdult, user::setAdult, userRequestDto.isAdult());
        return userRepository.save(user);

    }
    public List<UserEntity> getByRole(Long role) {

        Optional<Role> roleById = roleRepository.findById(role);
        if (roleById.isEmpty()) {
            throw new IllegalArgumentException("Role does not exist");
        }
        return userRepository.getUsersByRole(roleById);
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
