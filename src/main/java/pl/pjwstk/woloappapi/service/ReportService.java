package pl.pjwstk.woloappapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pjwstk.woloappapi.model.ReportEditRequestDto;
import pl.pjwstk.woloappapi.model.ReportRequestDto;
import pl.pjwstk.woloappapi.model.entities.Report;
import pl.pjwstk.woloappapi.model.translation.ReportTranslationResponce;
import pl.pjwstk.woloappapi.repository.EventRepository;
import pl.pjwstk.woloappapi.repository.ReportRepository;
import pl.pjwstk.woloappapi.utils.EventMapper;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final EventMapper eventMapper;
    private final EventRepository eventRepository;

    public Report getReportById(Long id){
        return reportRepository.findById(id).orElseThrow(() -> new NotFoundException("Report not found"));
    }

    public Report getPublicReportByEventId(Long id){
        return reportRepository.findAllByEventId(id)
                .stream()
                .filter(Report::isPublished)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Report not found"));
    }

    @Transactional
    public void createReport(ReportRequestDto reportDto, ReportTranslationResponce translation) {
        reportRepository.save(eventMapper.toReport(reportDto, translation)
                        .event(eventRepository.findById(reportDto.getEvent())
                                .orElseThrow(() -> new NotFoundException("Event id not found!")))
                        .build());
    }

    @Transactional
    public void updateReport(ReportEditRequestDto reportDto) {
        var report = reportRepository.findById(reportDto.getId())
                .orElseThrow(() -> new NotFoundException("Report id not found!"));
        report.setReportPL(reportDto.getReportPL());
        report.setReportEN(reportDto.getReportEN());
        report.setReportUA(reportDto.getReportUA());
        report.setReportRU(reportDto.getReportRU());
        report.setPublished(report.isPublished());
        reportRepository.save(report);
    }

    @Transactional
    public void deleteReport(Long id) {
        var report = reportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Report id not found!"));
        reportRepository.delete(report);
    }

    public List<Report> getAllReportsByEventId(Long eventId) {
        return reportRepository
                .findAllByEventId(eventId);
    }

    public void publishReport(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Report id not found!"));
        report.setPublished(true);
        reportRepository.save(report);
    }

    public void unpublishReport(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Report id not found!"));
        report.setPublished(false);
        reportRepository.save(report);
    }
}
