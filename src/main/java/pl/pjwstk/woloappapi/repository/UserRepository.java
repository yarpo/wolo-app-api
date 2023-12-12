package pl.pjwstk.woloappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.pjwstk.woloappapi.model.Role;
import pl.pjwstk.woloappapi.model.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> getUsersByRole(Optional<Role> role);

    Boolean existsByEmail(String email);
    Optional<UserEntity> findByEmail(String email);

}

