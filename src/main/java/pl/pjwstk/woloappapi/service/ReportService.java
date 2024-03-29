package pl.pjwstk.woloappapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pjwstk.woloappapi.model.entities.Report;
import pl.pjwstk.woloappapi.model.ReportDto;
import pl.pjwstk.woloappapi.repository.EventRepository;
import pl.pjwstk.woloappapi.repository.ReportRepository;
import pl.pjwstk.woloappapi.utils.EventMapper;
import pl.pjwstk.woloappapi.utils.NotFoundException;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final EventMapper eventMapper;
    private final EventRepository eventRepository;

    public Report getReportByEventId(Long id) {
        var event = eventRepository
                .findById(id).orElseThrow(() -> new NotFoundException("Event id not found!"));
        var report = event.getReport();
        if (report != null) {
            return report;
        }
        throw new NotFoundException("Report not found for this event!");
    }

    @Transactional
    public void createReport(ReportDto reportDto) {
        reportRepository.save(eventMapper.toReport(reportDto)
                        .event(eventRepository.findById(reportDto.getEvent())
                                .orElseThrow(() -> new NotFoundException("Event id not found!")))
                        .build());
    }

    @Transactional
    public void updateReport(ReportDto reportDto) {
        var report = eventRepository.findById(reportDto.getEvent())
                .orElseThrow(() -> new NotFoundException("Event id not found!"))
                        .getReport();
        report.setReport(reportDto.getReport());
        report.setPublished(report.isPublished());
        reportRepository.save(report);
    }

    @Transactional
    public void deleteReport(Long id) {
        var report = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event id not found!"))
                .getReport();
        reportRepository.delete(report);
    }
}
