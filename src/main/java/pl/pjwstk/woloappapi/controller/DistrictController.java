package pl.pjwstk.woloappapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjwstk.woloappapi.model.District;
import pl.pjwstk.woloappapi.model.DistrictDto;
import pl.pjwstk.woloappapi.service.DistrictService;
import pl.pjwstk.woloappapi.utils.DictionariesMapper;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/districts")
public class DistrictController {

    private final DistrictService districtService;
    private final DictionariesMapper dictionariesMapper;

    @GetMapping()
    public ResponseEntity<List<DistrictDto>> getDistricts() {
        List<District> districts = districtService.getAllDistricts();
        List<DistrictDto> districtDtos =
                districts.stream().map(dictionariesMapper::toDistrictDto).toList();
        return new ResponseEntity<>(districtDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DistrictDto> getDistrictById(@PathVariable Long id) {
        DistrictDto districtDto = dictionariesMapper
                .toDistrictDto(districtService.getDistrictById(id));
        return new ResponseEntity<>(districtDto, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addDistrict(@Valid @RequestBody District district) {
        districtService.createDistrict(district);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteDistrict(@PathVariable Long id) {
        districtService.deleteCDistrict(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<HttpStatus> editDistrict(
            @Valid @RequestBody District district, @PathVariable Long id) {
        districtService.updateDistrict(district, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
