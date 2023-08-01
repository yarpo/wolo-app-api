package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pjwstk.woloappapi.model.Event;
import pl.pjwstk.woloappapi.repository.CategoryRepository;
import pl.pjwstk.woloappapi.repository.EventRepository;
import pl.pjwstk.woloappapi.repository.OrganisationRepository;
import pl.pjwstk.woloappapi.utils.EventNotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final OrganisationRepository organisationRepository;
    private final CategoryRepository categoryRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event id not found!"));
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

//    public List<Event> getByOrganisation(Long organisation) {
//        Optional<Organisation> organisationById = organisationRepository.findById(organisation);
//        if(organisationById.isEmpty()){
//            throw new IllegalArgumentException("Organisation with ID " + organisation + " does not exist");
//        }
//        return eventRepository.getEventsByOrganisation(organisationById);
//    }
//
//    public List<Event> getByCategory(Long category) {
//        Optional<Category> categoryById = categoryRepository.findById(category);
//        if(categoryById.isEmpty()){
//            throw new IllegalArgumentException("Category does not exist");
//        }
//        return eventRepository.getEventsByCategory(categoryById);
//    }
}
