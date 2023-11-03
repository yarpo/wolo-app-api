package pl.pjwstk.woloappapi.repository;

import pl.pjwstk.woloappapi.model.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventRepositoryCustom {
    List<Event> findAllByFilter(String district, LocalDate startDate,LocalDate endDate,
                                Long category,Long organizer, Integer ageRestriction
                                );
}
