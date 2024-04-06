package pl.pjwstk.woloappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.pjwstk.woloappapi.model.entities.Shift;
import pl.pjwstk.woloappapi.model.entities.ShiftToUser;
import pl.pjwstk.woloappapi.model.entities.User;

import java.util.List;

@Repository
public interface ShiftToUserRepository extends JpaRepository<ShiftToUser, Long> {

    List<ShiftToUser> findShiftToUsersByUserId(Long userId);
    boolean existsByShiftAndUser(Shift shift, User user);
}
