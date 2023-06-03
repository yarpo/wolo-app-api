package pl.pjwstk.woloappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pjwstk.woloappapi.model.ShiftToUser;

public interface ShiftToUserRepository extends JpaRepository<ShiftToUser, Long> {
}
