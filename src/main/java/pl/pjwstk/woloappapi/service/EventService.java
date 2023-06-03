package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pjwstk.woloappapi.model.Event;
import pl.pjwstk.woloappapi.model.Organisation;
import pl.pjwstk.woloappapi.repository.EventRepository;
import pl.pjwstk.woloappapi.repository.OrganisationRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final OrganisationRepository organisationRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
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

    public List<Event> getByOrganisation(Long organisation) {
        Optional<Organisation> organisationById = organisationRepository.findById(organisation);
        if(organisationById.isEmpty()){
            throw new IllegalArgumentException("Organisation with ID " + organisation + " does not exist");
        }
        return eventRepository.getEventsByOrganisation(organisationById);
    }
}
