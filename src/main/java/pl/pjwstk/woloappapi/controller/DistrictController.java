package pl.pjwstk.woloappapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjwstk.woloappapi.model.DistrictResponseDto;
import pl.pjwstk.woloappapi.model.entities.District;
import pl.pjwstk.woloappapi.model.DistrictRequestDto;
import pl.pjwstk.woloappapi.service.DistrictService;
import pl.pjwstk.woloappapi.utils.DictionariesMapper;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/districts")
@Tag(name = "Districts")
public class DistrictController {

    private final DistrictService districtService;
    private final DictionariesMapper dictionariesMapper;

    @GetMapping()
    public ResponseEntity<List<DistrictResponseDto>> getDistricts() {
        List<District> districts = districtService.getAllDistricts();
        List<DistrictResponseDto> districtDtos =
                districts.stream().map(dictionariesMapper::toDistrictDto).toList();
        return new ResponseEntity<>(districtDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DistrictResponseDto> getDistrictById(@PathVariable Long id) {
        DistrictResponseDto districtDto = dictionariesMapper
                .toDistrictDto(districtService.getDistrictById(id));
        return new ResponseEntity<>(districtDto, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addDistrict(@Valid @RequestBody DistrictRequestDto district) {
        districtService.createDistrict(district);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteDistrict(@PathVariable Long id) {
        districtService.deleteDistrict(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/edit")
    public ResponseEntity<HttpStatus> editDistrict(
            @Valid @RequestBody DistrictRequestDto district) {
        districtService.updateDistrict(district);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
