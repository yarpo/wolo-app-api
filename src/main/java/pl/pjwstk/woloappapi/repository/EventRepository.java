package pl.pjwstk.woloappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pjwstk.woloappapi.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryCustom {

}


