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
import pl.pjwstk.woloappapi.model.ReportDto;
import pl.pjwstk.woloappapi.service.EventService;
import pl.pjwstk.woloappapi.service.ReportService;
import pl.pjwstk.woloappapi.utils.EventMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/reports")
@Tag(name = "Report")
public class ReportController {
    private final ReportService reportService;
    private final EventMapper eventMapper;

    private final EventService eventService;
    @Operation(
            summary = "Get report by id",
            description = "report must exist",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200" ,
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ReportDto.class)
                                    )
                            }
                    )
            },
            parameters = {
                    @Parameter(name = "id",
                            description = "Report id",
                            example = "1"
                    )
            }
    )
    @GetMapping("/one/{id}")
    public ResponseEntity<ReportDto> getReportById(@PathVariable Long id) {
        var report = eventMapper.toReportDto(reportService.getReportById(id));
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReportDto>> getAllReportsByEventId(@RequestParam(value = "eventId") Long eventId){
        var reports = reportService.getAllReportsByEventId(eventId)
                .stream()
                .map(eventMapper::toReportDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @Operation(
            summary = "Adding report",
            description = "id = null",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201"
                    )
            },
            parameters = {
                    @Parameter(name = "report",
                            description = "Report object to create",
                            schema = @Schema(implementation = ReportDto.class)
                    )
            }
    )
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addReport(@RequestBody ReportDto reportDto) {
        reportService.createReport(reportDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete report",
            responses = {
                    @ApiResponse(
                            description = "No content",
                            responseCode = "204"
                    )
            },
            parameters = {
                    @Parameter(name = "id",
                            description = "Report id",
                            example = "1"
                    )
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Edit report",
            description = "Report must exist",
            responses = {
                    @ApiResponse(
                            description = "No content",
                            responseCode = "204"
                    )
            },
            parameters = {
                    @Parameter(name = "report",
                            description = "Report object with changes",
                            schema = @Schema(implementation = ReportDto.class)
                    )
            }
    )
    @PutMapping("/edit")
    public ResponseEntity<HttpStatus> editReport(
            @Valid @RequestBody ReportDto reportDto) {
        reportService.updateReport(reportDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
