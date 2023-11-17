package pl.pjwstk.woloappapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjwstk.woloappapi.model.District;
import pl.pjwstk.woloappapi.service.DistrictService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/districts")
public class DistrictController {

    private final DistrictService districtService;

    @GetMapping()
    public ResponseEntity<List<District>> getDistricts(){
        List<District> districts = districtService.getAllDistricts();
        return new ResponseEntity<>(districts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<District> getDistrictById(@PathVariable Long id){
        return new ResponseEntity<>(districtService.getDistrictById(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addDistrict(@RequestBody District district){
        districtService.createDistrict(district);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteDistrict(@PathVariable Long id){
        districtService.deleteCDistrict(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
