package pl.pjwstk.woloappapi.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pl.pjwstk.woloappapi.model.Role;
import pl.pjwstk.woloappapi.model.User;
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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("User id not found!"));
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public User updateUser(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new IllegalArgumentException("User with ID " + user.getId() + " does not exist");
        }
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User with ID " + id + " does not exist");
        }
        userRepository.deleteById(id);
    }
    @Transactional
    public User updateUser(@Valid User user, Long id) {

        Optional<User> existingUserOptional = userRepository.findById(id);
        if (existingUserOptional.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + id + " does not exist");
        }


        User existingUser = existingUserOptional.get();
        existingUser.setEmail(user.getEmail());
        existingUser.setFirstname(user.getFirstname());
        existingUser.setLastname(user.getLastname());
        existingUser.setAdult(user.isAdult());
        existingUser.setAgreementSigned(user.isAgreementSigned());
        existingUser.setPeselVerified(user.isPeselVerified());
        existingUser.setPassword_hash(user.getPassword_hash());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setRole(user.getRole());
        existingUser.setSalt(user.getSalt());


        return userRepository.save(existingUser);
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
            return 0; // lub można zwrócić odpowiedni kod błędu
        }
    }
}
