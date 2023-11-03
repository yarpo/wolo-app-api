package pl.pjwstk.woloappapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjwstk.woloappapi.model.Event;
import pl.pjwstk.woloappapi.service.EventService;

import java.time.LocalDate;
import java.util.List;

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

    @GetMapping()
    public ResponseEntity<List<Event>> filterEvents(@RequestParam(value = "district", required = false) String district,
                                                    @RequestParam(value = "startDate", required = false) LocalDate startDate,
                                                    @RequestParam(value = "endDate", required = false) LocalDate endDate,
                                                    @RequestParam(value = "category", required = false) Long category,
                                                    @RequestParam(value = "organizer", required = false) Long organizer,
                                                    @RequestParam(value = "ageRestriction", required = false) Integer ageRestriction) {
        List<Event> filteredEvents = eventService.filterEvents(district, startDate, endDate, category, organizer, ageRestriction);
        return new ResponseEntity<>(filteredEvents, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id){
        Event event = eventService.getEventById(id);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addEvent(@RequestBody Event event){
        eventService.createEvent(event);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

