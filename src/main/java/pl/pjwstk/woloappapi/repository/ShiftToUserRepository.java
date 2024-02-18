package pl.pjwstk.woloappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pjwstk.woloappapi.model.Shift;
import pl.pjwstk.woloappapi.model.ShiftToUser;
import pl.pjwstk.woloappapi.model.UserEntity;

@Repository
public interface ShiftToUserRepository extends JpaRepository<ShiftToUser, Long> {
    boolean existsByShiftAndUser(Shift shift, UserEntity user);
}
