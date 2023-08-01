package pl.pjwstk.woloappapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjwstk.woloappapi.model.Shift;
import pl.pjwstk.woloappapi.service.ShiftService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/shift")
public class ShiftController {

    private final ShiftService shiftService;

    @GetMapping("/all")
    public ResponseEntity<List<Shift>> getShifts(){
        List<Shift> Shifts = shiftService.getAllShifts();
        return new ResponseEntity<>(Shifts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shift> getShiftById(@PathVariable Long id){
        return new ResponseEntity<>(shiftService.getShiftById(id), HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addShift(@RequestBody Shift Shift){
        shiftService.createShift(Shift);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteShift(@PathVariable Long id){
        shiftService.deleteShift(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}