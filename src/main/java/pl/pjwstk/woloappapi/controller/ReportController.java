package pl.pjwstk.woloappapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import pl.pjwstk.woloappapi.model.ReportRequestDto;
import pl.pjwstk.woloappapi.model.ReportResponceDto;
import pl.pjwstk.woloappapi.model.translation.ReportTranslationRequest;
import pl.pjwstk.woloappapi.model.translation.ReportTranslationResponce;
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

    @GetMapping("/public/{id}")
    public ResponseEntity<ReportResponceDto> getPublicReportByEventId(@PathVariable Long id) {
        var report = eventMapper.toReportResponceDto(reportService.getPublicReportByEventId(id));
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReportResponceDto>> getAllReportsByEventId(@RequestParam(value = "eventId") Long eventId){
        var reports = reportService.getAllReportsByEventId(eventId)
                .stream()
                .map(eventMapper::toReportResponceDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addReport(@RequestBody ReportRequestDto reportDto, @RequestParam String language) {
        var translate = createTranslationDto(reportDto, language);
        var localClient = WebClient.create("http://host.docker.internal:5000/");
        localClient.post()
                .uri("/report/translate")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(translate)
                .retrieve()
                .bodyToMono(ReportTranslationResponce.class)
                .subscribe(translated -> reportService.createReport(reportDto, translated));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/edit")
    public ResponseEntity<HttpStatus> editReport(
            @Valid @RequestBody ReportRequestDto reportDto,
            @RequestParam String language) {
        var translate = createTranslationDto(reportDto, language);
        var localClient = WebClient.create("http://host.docker.internal:5000/");
        localClient.post()
                .uri("/report/translate")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(translate)
                .retrieve()
                .bodyToMono(ReportTranslationResponce.class)
                .subscribe(translated -> reportService.updateReport(reportDto, translated));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private ReportTranslationRequest createTranslationDto(ReportRequestDto reportDto, String language) {
        var translate = new ReportTranslationRequest();
        translate.setLanguage(language);
        translate.setReport(reportDto.getReport());
        return translate;
    }
}
