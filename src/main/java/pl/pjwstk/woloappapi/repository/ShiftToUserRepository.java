package pl.pjwstk.woloappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.pjwstk.woloappapi.model.entities.Event;
import pl.pjwstk.woloappapi.model.entities.ShiftToUser;

import java.util.List;

@Repository
public interface ShiftToUserRepository extends JpaRepository<ShiftToUser, Long> {

    List<ShiftToUser> findShiftToUsersByUserId(Long userId);

    @Query("DELETE FROM ShiftToUser stu " +
            "WHERE stu.shift.event = :event " +
            "AND stu.isOnReserveList = true")
    void deleteByShiftEventAndIsOnReserveListTrue(Event event);
}
