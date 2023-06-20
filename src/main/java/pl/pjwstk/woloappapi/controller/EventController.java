package pl.pjwstk.woloappapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjwstk.woloappapi.model.Event;
import pl.pjwstk.woloappapi.service.EventService;

import java.util.List;

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
        return new ResponseEntity<>(eventService.getEventById(id), HttpStatus.OK);
    }

    @GetMapping("/org/{organisation}")
    public ResponseEntity<List<Event>> getEventsByOrganisation(@PathVariable Long organisation){
        return new ResponseEntity<>(eventService.getByOrganisation(organisation), HttpStatus.OK);
    }

    @GetMapping("/cat/{category}")
    public ResponseEntity<List<Event>> getEventsByCategory(@PathVariable Long category){
        return new ResponseEntity<>(eventService.getByCategory(category), HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addEvent(@RequestBody Event event){
        eventService.createEvent(event);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }

}

