package pl.pjwstk.woloappapi.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;
import pl.pjwstk.woloappapi.model.entities.Shift;
import pl.pjwstk.woloappapi.model.entities.ShiftToUser;
import pl.pjwstk.woloappapi.model.entities.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PDFGenerationService {
    public byte[] generatePDFForAllShifts(List<Shift> shifts) throws IOException {
        var document = new PDDocument();

        shifts.forEach(shift -> {
            PDPage page = new PDPage();
            document.addPage(page);

            try (var contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 700);
                List<User> users = shift.getShiftToUsers().stream()
                        .map(ShiftToUser::getUser).toList();

                users.forEach(user -> {
                    try {
                        String userInfo = String.format("Name: %s, Phone number: %s, Email: %s",
                                user.getFirstName(), user.getPhoneNumber(), user.getEmail());
                        contentStream.showText(userInfo);
                        contentStream.newLineAtOffset(0, -15);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                contentStream.endText();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        var byteArrayOutputStream = new ByteArrayOutputStream();
        document.save(byteArrayOutputStream);
        document.close();

        return byteArrayOutputStream.toByteArray();
    }
}
