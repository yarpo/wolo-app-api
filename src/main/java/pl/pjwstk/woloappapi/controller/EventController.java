package pl.pjwstk.woloappapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.pjwstk.woloappapi.model.Event;
import pl.pjwstk.woloappapi.repository.EventRepository;

@RestController
public class EventController {

    private EventRepository EventRepository;

    @Autowired
    public EventController(EventRepository EventRepository) {
        this.EventRepository = EventRepository;
    }

    @GetMapping("/Event/all")
    Iterable<Event> all() {
        return EventRepository.findAll();
    }

    @GetMapping("/Event/{id}")
    Event EventById(@PathVariable Long id) {
        return EventRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND));
    }

    @PostMapping("/Event/save")
    Event save(@RequestBody Event Event) {
        return EventRepository.save(Event);
    }

}