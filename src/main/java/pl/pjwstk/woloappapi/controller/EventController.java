package pl.pjwstk.woloappapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjwstk.woloappapi.model.Event;
import pl.pjwstk.woloappapi.service.EventService;
import pl.pjwstk.woloappapi.utils.EventNotFoundException;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;

    @GetMapping("/all")
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
            throw new EventNotFoundException("Event id not found!");
        }
    }

    @GetMapping("/{organisation}")
    public ResponseEntity<List<Event>> getEventByOrganisation(@PathVariable Long organisation){
        return new ResponseEntity<>(eventService.getByOrganisation(organisation), HttpStatus.OK);

    }
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addEvent(@RequestBody Event event){
        eventService.createEvent(event);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }


}

