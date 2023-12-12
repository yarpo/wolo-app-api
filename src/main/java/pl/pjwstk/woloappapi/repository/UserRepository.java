package pl.pjwstk.woloappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.pjwstk.woloappapi.model.Role;
import pl.pjwstk.woloappapi.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> getUsersByRole(Optional<Role> role);


    User findByEmail(String email);

}

