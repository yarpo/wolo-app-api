package pl.pjwstk.woloappapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjwstk.woloappapi.model.District;
import pl.pjwstk.woloappapi.model.DistrictDto;
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

    @Operation(
            summary = "Get all districts",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(type = "array",implementation = DistrictDto.class)
                                    )
                            }
                    )
            }
    )
    @GetMapping()
    public ResponseEntity<List<DistrictDto>> getDistricts() {
        List<District> districts = districtService.getAllDistricts();
        List<DistrictDto> districtDtos =
                districts.stream().map(dictionariesMapper::toDistrictDto).toList();
        return new ResponseEntity<>(districtDtos, HttpStatus.OK);
    }

    @Operation(
            summary = "Get district by id",
            description = "District must exist",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200" ,
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = DistrictDto.class)
                                    )
                            }
                    )
            },
            parameters = {
                    @Parameter(name = "id",
                            description = "District id",
                            example = "1"
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<DistrictDto> getDistrictById(@PathVariable Long id) {
        DistrictDto districtDto = dictionariesMapper
                .toDistrictDto(districtService.getDistrictById(id));
        return new ResponseEntity<>(districtDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Adding new district",
            description = "id = null",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201"
                    )
            },
            parameters = {
                    @Parameter(name = "district",
                            description = "District object to create",
                            schema = @Schema(implementation = DistrictDto.class)
                    )
            }
    )
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addDistrict(@Valid @RequestBody DistrictDto district) {
        districtService.createDistrict(district);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete district",
            description = "District will not be removed from DB, but isOld property will became true",
            responses = {
                    @ApiResponse(
                            description = "No content",
                            responseCode = "204"
                    )
            },
            parameters = {
                    @Parameter(name = "id",
                            description = "District id",
                            example = "1"
                    )
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteDistrict(@PathVariable Long id) {
        districtService.deleteDistrict(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Edit district",
            description = "District must exist",
            responses = {
                    @ApiResponse(
                            description = "No content",
                            responseCode = "204"
                    )
            },
            parameters = {
                    @Parameter(name = "district",
                            description = "district object with changes",
                            schema = @Schema(implementation = DistrictDto.class)
                    )
            }
    )
    @PutMapping("/edit")
    public ResponseEntity<HttpStatus> editDistrict(
            @Valid @RequestBody DistrictDto district) {
        districtService.updateDistrict(district);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
