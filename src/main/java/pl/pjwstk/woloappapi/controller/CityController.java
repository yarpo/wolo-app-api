package pl.pjwstk.woloappapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjwstk.woloappapi.model.CityDto;
import pl.pjwstk.woloappapi.model.DistrictDto;
import pl.pjwstk.woloappapi.model.entities.City;
import pl.pjwstk.woloappapi.model.entities.District;
import pl.pjwstk.woloappapi.service.CityService;
import pl.pjwstk.woloappapi.service.DistrictService;
import pl.pjwstk.woloappapi.utils.DictionariesMapper;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/cities")
@Tag(name = "Cities")
public class CityController {

    private final CityService cityService;
    private final DictionariesMapper dictionariesMapper;

    @GetMapping()
    public ResponseEntity<List<CityDto>> getCities() {
        List<City> cities = cityService.getAllCities();
        List<CityDto> cityDtos =
                cities.stream().map(dictionariesMapper::toCityDto).toList();
        return new ResponseEntity<>(cityDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDto> getCityById(@PathVariable Long id) {
        var cityDto = dictionariesMapper
                .toCityDto(cityService.getCityById(id));
        return new ResponseEntity<>(cityDto, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addCity(@Valid @RequestBody CityDto cityDto) {
        cityService.createCity(cityDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/edit")
    public ResponseEntity<HttpStatus> editCity(
            @Valid @RequestBody CityDto cityDto) {
        cityService.updateCity(cityDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
