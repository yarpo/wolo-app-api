package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pjwstk.woloappapi.model.Event;
import pl.pjwstk.woloappapi.repository.EventRepository;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

     public List<Event> getAllEvents() {
         return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event id not found!"));
    }

    public void createEvent(Event event) {
        eventRepository.save(event);
    }

    public Event updateEvent(Event event) {
        if (!eventRepository.existsById(event.getId())) {
            throw new IllegalArgumentException("Event with ID " + event.getId() + " does not exist");
        }
        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new IllegalArgumentException("Event with ID " + id + " does not exist");
        }
        eventRepository.deleteById(id);
    }

    public List<Event> filterEvents(String[] localizations, LocalDate startDate, LocalDate endDate,
                                    Long category, Long organizer, Integer ageRestriction,
                                    boolean isPeselVerificationRequired) {
        return eventRepository.findAllByFilter(localizations, startDate, endDate,
                category, organizer, ageRestriction, isPeselVerificationRequired);
    }

}
