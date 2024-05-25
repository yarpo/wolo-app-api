package pl.pjwstk.woloappapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import pl.pjwstk.woloappapi.model.ReportEditRequestDto;
import pl.pjwstk.woloappapi.model.ReportRequestDto;
import pl.pjwstk.woloappapi.model.ReportResponceDto;
import pl.pjwstk.woloappapi.model.translation.ReportTranslationRequest;
import pl.pjwstk.woloappapi.model.translation.ReportTranslationResponce;
import pl.pjwstk.woloappapi.service.EventService;
import pl.pjwstk.woloappapi.service.ReportService;
import pl.pjwstk.woloappapi.service.UserService;
import pl.pjwstk.woloappapi.utils.EventMapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/reports")
@Tag(name = "Report")
public class ReportController {
    private final ReportService reportService;
    private final EventMapper eventMapper;

    private final EventService eventService;
    private final UserService userService;

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
            @Valid @RequestBody ReportEditRequestDto reportDto) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var organisationId = userService.getCurrentUser(authentication).getOrganisation().getId();
        var organisation = eventService.getEventById(reportDto.getEvent()).getOrganisation();
        if(Objects.equals(organisationId, organisation.getId())) {
            reportService.updateReport(reportDto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            throw new IllegalArgumentException("You can edit reports only for your organisation");
        }
    }

    @PutMapping("/admin/edit")
    public ResponseEntity<HttpStatus> editReportByAdmin(
            @Valid @RequestBody ReportEditRequestDto reportDto) {
        reportService.updateReport(reportDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/admin/publish/{id}")
    public ResponseEntity<HttpStatus> publishReportByAdmin(@PathVariable Long id){
        reportService.publishReport(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/publish/{id}")
    public ResponseEntity<HttpStatus> publishReport(@PathVariable Long id){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var organisationId = userService.getCurrentUser(authentication).getOrganisation().getId();
        var organisation = reportService.getReportById(id).getEvent().getOrganisation();
        if(Objects.equals(organisationId, organisation.getId())) {
            reportService.publishReport(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            throw new IllegalArgumentException("You can publish reports only for your organisation");
        }
    }

    @PostMapping("/admin/unpublish/{id}")
    public ResponseEntity<HttpStatus> unpublishReportByAdmin(@PathVariable Long id){
        reportService.unpublishReport(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/unpublish/{id}")
    public ResponseEntity<HttpStatus> unpublishReport(@PathVariable Long id){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var organisationId = userService.getCurrentUser(authentication).getOrganisation().getId();
        var organisation = reportService.getReportById(id).getEvent().getOrganisation();
        if(Objects.equals(organisationId, organisation.getId())) {
            reportService.unpublishReport(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            throw new IllegalArgumentException("You can publish reports only for your organisation");
        }
    }

    private ReportTranslationRequest createTranslationDto(ReportRequestDto reportDto, String language) {
        var translate = new ReportTranslationRequest();
        translate.setLanguage(language);
        translate.setReport(reportDto.getReport());
        return translate;
    }
}
