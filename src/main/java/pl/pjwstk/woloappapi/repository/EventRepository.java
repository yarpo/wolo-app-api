package pl.pjwstk.woloappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pjwstk.woloappapi.model.Category;
import pl.pjwstk.woloappapi.model.Event;
import pl.pjwstk.woloappapi.model.Organisation;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> getEventsByOrganisation(Optional<Organisation> organisation);
    List<Event> getEventsByCategory(Optional<Category> category);
}
