package pl.pjwstk.woloappapi.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.pjwstk.woloappapi.model.entities.Shift;
import pl.pjwstk.woloappapi.model.entities.ShiftToUser;
import pl.pjwstk.woloappapi.model.entities.User;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.when;

public class PDFGenerationServiceTest {
    @Mock
    private Shift mockShift;

    @Mock
    private User mockUser;

    @Test
    public void testGeneratePDFForAllShifts() throws IOException {
        MockitoAnnotations.openMocks(this);
        when(mockUser.getFirstName()).thenReturn("John");
        when(mockUser.getLastName()).thenReturn("Doe");
        when(mockUser.getEmail()).thenReturn("john.doe@example.com");
        when(mockUser.getPhoneNumber()).thenReturn("123456789");
        when(mockShift.getShiftToUsers()).thenReturn(List.of(new ShiftToUser(mockUser, null)));

        PDFGenerationService pdfGenerationService = new PDFGenerationService();

        byte[] pdfBytes = pdfGenerationService.generatePDFForAllShifts(List.of(mockShift));

        assert(pdfBytes.length > 0);

        try (PDDocument document = Loader.loadPDF(pdfBytes)) {
            assert(document.getNumberOfPages() == 1);
            PDFTextStripper textStripper = new PDFTextStripper();
            String pdfText = textStripper.getText(document);

            assert(pdfText.contains("John"));
            assert(pdfText.contains("john.doe@example.com"));
            assert(pdfText.contains("123456789"));
        }
    }
}
