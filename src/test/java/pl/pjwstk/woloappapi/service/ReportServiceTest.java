package pl.pjwstk.woloappapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.ReportEditRequestDto;
import pl.pjwstk.woloappapi.model.ReportRequestDto;
import pl.pjwstk.woloappapi.model.entities.Event;
import pl.pjwstk.woloappapi.model.entities.Report;
import pl.pjwstk.woloappapi.model.translation.ReportTranslationResponce;
import pl.pjwstk.woloappapi.repository.EventRepository;
import pl.pjwstk.woloappapi.repository.ReportRepository;
import pl.pjwstk.woloappapi.utils.EventMapper;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {
    @Mock
    private ReportRepository reportRepository;

    @Mock
    private EventMapper eventMapper;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private ReportService reportService;

    private Report report;
    private Event event;
    private ReportRequestDto reportRequestDto;
    private ReportTranslationResponce translation;
    private Report.ReportBuilder reportBuilder;

    @BeforeEach
    public void setUp() {
        report = new Report();
        report.setId(1L);
        report.setPublished(false);

        event = new Event();
        event.setId(1L);

        reportRequestDto = new ReportRequestDto();
        reportRequestDto.setEvent(1L);
        reportRequestDto.setPublished(false);

        translation = new ReportTranslationResponce();
        translation.setReportPL("Polish report");
        translation.setReportEN("English report");
        translation.setReportUA("Ukrainian report");
        translation.setReportRU("Russian report");

        reportBuilder = Report.builder()
                .reportPL(translation.getReportPL())
                .reportEN(translation.getReportEN())
                .reportUA(translation.getReportUA())
                .reportRU(translation.getReportRU())
                .published(reportRequestDto.isPublished());
    }

    @Test
    public void testGetReportById_ReportExists() {
        when(reportRepository.findById(1L)).thenReturn(Optional.of(report));

        Report result = reportService.getReportById(1L);

        assertEquals(report, result);
        verify(reportRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetReportById_ReportNotExists() {
        when(reportRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            reportService.getReportById(1L);
        });

        verify(reportRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetPublicReportByEventId_ReportExistsAndPublished() {
        report.setPublished(true);
        when(reportRepository.findAllByEventId(1L)).thenReturn(List.of(report));

        Report result = reportService.getPublicReportByEventId(1L);

        assertEquals(report, result);
        verify(reportRepository, times(1)).findAllByEventId(1L);
    }

    @Test
    public void testGetPublicReportByEventId_ReportNotExistsOrUnpublished() {
        when(reportRepository.findAllByEventId(1L)).thenReturn(List.of(report));

        assertThrows(NotFoundException.class, () -> {
            reportService.getPublicReportByEventId(1L);
        });

        verify(reportRepository, times(1)).findAllByEventId(1L);
    }

    @Test
    public void testCreateReport() {
        Report.ReportBuilder reportBuilder = mock(Report.ReportBuilder.class);
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(eventMapper.toReport(reportRequestDto, translation)).thenReturn(reportBuilder);
        when(reportBuilder.event(event)).thenReturn(reportBuilder);
        when(reportBuilder.build()).thenReturn(report);

        reportService.createReport(reportRequestDto, translation);

        verify(reportRepository, times(1)).save(report);
        verify(eventRepository, times(1)).findById(1L);
        verify(eventMapper, times(1)).toReport(reportRequestDto, translation);
        verify(reportBuilder, times(1)).event(event);
        verify(reportBuilder, times(1)).build();
    }

    @Test
    public void testUpdateReport() {
        ReportEditRequestDto reportDto = new ReportEditRequestDto();
        reportDto.setId(1L);
        reportDto.setReportPL("Updated Report PL");
        when(reportRepository.findById(1L)).thenReturn(Optional.of(report));

        reportService.updateReport(reportDto);

        assertEquals(reportDto.getReportPL(), report.getReportPL());
        verify(reportRepository, times(1)).save(report);
    }

    @Test
    public void testDeleteReport() {
        when(reportRepository.findById(1L)).thenReturn(Optional.of(report));

        reportService.deleteReport(1L);

        verify(reportRepository, times(1)).delete(report);
    }

    @Test
    public void testGetAllReportsByEventId() {
        when(reportRepository.findAllByEventId(1L)).thenReturn(List.of(report));

        List<Report> result = reportService.getAllReportsByEventId(1L);

        assertEquals(1, result.size());
        assertEquals(report, result.get(0));
        verify(reportRepository, times(1)).findAllByEventId(1L);
    }

    @Test
    public void testPublishReport() {
        when(reportRepository.findById(1L)).thenReturn(Optional.of(report));

        reportService.publishReport(1L);

        assertTrue(report.isPublished());
        verify(reportRepository, times(1)).save(report);
    }

    @Test
    public void testUnpublishReport() {
        report.setPublished(true);
        when(reportRepository.findById(1L)).thenReturn(Optional.of(report));

        reportService.unpublishReport(1L);

        assertFalse(report.isPublished());
        verify(reportRepository, times(1)).save(report);
    }
}
