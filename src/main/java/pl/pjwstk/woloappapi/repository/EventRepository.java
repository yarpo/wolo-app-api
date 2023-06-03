package pl.pjwstk.woloappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.pjwstk.woloappapi.model.Event;
import pl.pjwstk.woloappapi.model.Organisation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> getEventsByOrganisation(Optional<Organisation> organisation);
    @Query("SELECT e FROM event e JOIN e.address a JOIN a.shifts s " +
            "WHERE s.startTime >= :startTime AND s.endTime <= :endTime")
    List<Event> findByAddressContainingShifts(
            @Param("startTime") LocalDate startTime,
            @Param("endTime") LocalDate endTime);
}
