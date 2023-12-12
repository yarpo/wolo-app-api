package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pl.pjwstk.woloappapi.model.Role;
import pl.pjwstk.woloappapi.model.UserEntity;
import pl.pjwstk.woloappapi.repository.RoleRepository;
import pl.pjwstk.woloappapi.repository.UserRepository;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("User id not found!"));
    }

    public void createUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    public UserEntity updateUser(UserEntity userEntity) {
        if (!userRepository.existsById(userEntity.getId())) {
            throw new IllegalArgumentException("User with ID " + userEntity.getId() + " does not exist");
        }
        return userRepository.save(userEntity);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User with ID " + id + " does not exist");
        }
        userRepository.deleteById(id);
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
            return 0; // lub można zwrócić odpowiedni kod błędu
        }
    }
}
