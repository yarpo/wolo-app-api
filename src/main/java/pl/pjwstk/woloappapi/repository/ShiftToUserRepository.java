package pl.pjwstk.woloappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pjwstk.woloappapi.model.ShiftToUser;
@Repository
public interface ShiftToUserRepository extends JpaRepository<ShiftToUser, Long> {
}
