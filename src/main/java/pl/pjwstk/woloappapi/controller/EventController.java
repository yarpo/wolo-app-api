package pl.pjwstk.woloappapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjwstk.woloappapi.model.Event;
import pl.pjwstk.woloappapi.service.EventService;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @GetMapping()
    public ResponseEntity<List<Event>> getEvents(){
        List<Event> events = eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id){
        Optional<Event> optionalEvent = eventService.getEventById(id);
        if (optionalEvent.isPresent()){
            return new ResponseEntity<>(optionalEvent.get(), HttpStatus.OK);
        } else {
            throw new NotFoundException("Event id not found!");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addEvent(@RequestBody Event event){
        eventService.createEvent(event);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

