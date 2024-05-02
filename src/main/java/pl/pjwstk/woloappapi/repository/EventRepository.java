package pl.pjwstk.woloappapi.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.pjwstk.woloappapi.model.entities.Event;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryCustom {
    @Query("SELECT DISTINCT e FROM Event e " +
            "JOIN e.shifts s " +
            "JOIN s.shiftToUsers stu " +
            "JOIN stu.user u " +
            "WHERE u.id = :userId")
    List<Event> findEventsByUserId(@Param("userId") Long userId);

    @Query("SELECT e FROM Event e " +
            "WHERE e.date > CURRENT_DATE " +
            "AND e.approved = true ")
    List<Event> findAllNotBeforeNow();

    @Query("SELECT e FROM Event e " +
            "WHERE e.date < :thresholdDate " +
            "AND e.date > CURRENT_DATE " +
            "AND e.approved = true " +
            "ORDER BY e.date ASC")
    List<Event> findEventsForTheyNeedYou(LocalDate thresholdDate);

    @Query("SELECT e FROM Event e " +
            "WHERE e.date > CURRENT_DATE " +
            "AND e.approved = true " +
            "ORDER BY e.date ASC")
    List<Event> findNearestEventsSortedByDate(Pageable pageable);
}
