package pl.pjwstk.woloappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pjwstk.woloappapi.model.entities.Shift;
import pl.pjwstk.woloappapi.model.entities.ShiftToUser;
import pl.pjwstk.woloappapi.model.entities.User;

@Repository
public interface ShiftToUserRepository extends JpaRepository<ShiftToUser, Long> {
    boolean existsByShiftAndUser(Shift shift, User user);
}
