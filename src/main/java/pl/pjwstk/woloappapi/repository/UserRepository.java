package pl.pjwstk.woloappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;
import pl.pjwstk.woloappapi.model.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u " +
            "JOIN u.roles r " +
            "WHERE r.name = :roleName")
    List<User> findUsersByRole(String roleName);

    @Query("SELECT DISTINCT u FROM User u " +
            "JOIN u.shifts uts " +
            "JOIN uts.shift s " +
            "WHERE s.id = :shiftId")
    List<User> findAllByShiftId(@Param("shiftId") Long shiftId);

    @Query("SELECT DISTINCT u " +
            "FROM User u " +
            "LEFT JOIN FETCH u.roles "+
            "WHERE u.email = :email")
    Optional<User> findUserWithRolesByEmail(@Param("email") String email);
}
