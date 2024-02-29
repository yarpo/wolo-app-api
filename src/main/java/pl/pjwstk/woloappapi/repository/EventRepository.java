package pl.pjwstk.woloappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.pjwstk.woloappapi.model.Event;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryCustom {
    @Query("SELECT DISTINCT e FROM Event e " +
            "JOIN e.addressToEvents ae " +
            "JOIN ae.shifts s " +
            "JOIN s.shiftToUsers stu " +
            "JOIN stu.user u " +
            "WHERE u.id = :userId")
    List<Event> findEventsByUserId(@Param("userId") Long userId);

    @Query("SELECT e FROM Event e " +
            "JOIN e.addressToEvents ae " +
            "JOIN ae.shifts s " +
            "WHERE s.date >= CURRENT_DATE")
    List<Event> findAllNotBeforeNow();
}
