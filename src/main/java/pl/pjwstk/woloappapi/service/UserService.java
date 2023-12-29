package pl.pjwstk.woloappapi.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pl.pjwstk.woloappapi.model.*;
import pl.pjwstk.woloappapi.repository.RoleRepository;
import pl.pjwstk.woloappapi.repository.UserRepository;
import pl.pjwstk.woloappapi.utils.NotFoundException;
import pl.pjwstk.woloappapi.utils.OrganisationMapper;
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
    private final UserMapper userMapper;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("User id not found!"));
    }
    public void createUser(UserRequestDto userDto) {
        User user = userMapper.toUser(userDto);
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User with ID " + id + " does not exist");
        }
        userRepository.deleteById(id);
    }
    public User updateUser(UserRequestDto userRequestDto, Long id) {

         User user = userRepository.findById(id).orElseThrow(
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
    public List<User> getByRole(Long role) {
        Optional<Role> roleById = roleRepository.findById(role);
        if (roleById.isEmpty()) {
            throw new IllegalArgumentException("Role does not exist");
        }
        return userRepository.getUsersByRole(roleById);
    }

    public int getShiftsCountForUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            return user.getShifts().size();
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
}
