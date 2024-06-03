package pl.pjwstk.woloappapi;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import pl.pjwstk.woloappapi.model.entities.Event;
import pl.pjwstk.woloappapi.model.entities.Shift;
import pl.pjwstk.woloappapi.repository.EventRepository;
import pl.pjwstk.woloappapi.utils.EmailUtil;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailUtilTest {
    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EmailUtil emailUtil;

    @Test
    public void testSendResetPasswordEmail() throws MessagingException {
        var email = "test@example.com";
        var mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailUtil.sendResetPasswordEmail(email);

        verify(javaMailSender, times(1)).send(mimeMessage);
    }

    @Test
    public void testSendDeleteEventMessage() throws MessagingException {
        var email = "test@example.com";
        var eventId = 1L;
        var event = Event.builder().nameEN("Sample Event").build();
        var mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        emailUtil.sendDeleteEventMessage(email, eventId);

        verify(javaMailSender, times(1)).send(mimeMessage);
    }

    @Test
    public void testSendEditEventMail() throws MessagingException {
        var email = "test@example.com";
        var eventId = 1L;
        var event = Event.builder().nameEN("Sample Event").build();
        var mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        emailUtil.sendEditEventMail(email, eventId);

        verify(javaMailSender, times(1)).send(mimeMessage);
    }

    @Test
    public void testSendPeselVerificationEmail() throws MessagingException {
        var email = "test@example.com";
        var eventId = 1L;
        var event = Event.builder().nameEN("Sample Event").build();
        var mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        emailUtil.sendPeselVerificationEmail(email, eventId);

        verify(javaMailSender, times(1)).send(mimeMessage);
    }

    @Test
    public void testSendAgreementNeededEmail() throws MessagingException {
        var email = "test@example.com";
        var eventId = 1L;
        var event = Event.builder().nameEN("Sample Event").build();
        var mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        emailUtil.sendAgreementNeededEmail(email, eventId);

        verify(javaMailSender, times(1)).send(mimeMessage);
    }

    @Test
    public void testSendUpdateCapacityEmail() throws MessagingException {
        var email = "test@example.com";
        var eventId = 1L;
        var event = Event.builder().nameEN("Sample Event").build();
        var mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        emailUtil.sendUpdateCapacityEmail(email, eventId);

        verify(javaMailSender, times(1)).send(mimeMessage);
    }

    @Test
    public void testSendMinAgeMail() throws MessagingException {
        var email = "test@example.com";
        var eventId = 1L;
        var event = Event.builder().nameEN("Sample Event").build();
        var mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        emailUtil.sendMinAgeMail(email, eventId);

        verify(javaMailSender, times(1)).send(mimeMessage);
    }

    @Test
    public void testSendJoinEventMail() throws MessagingException {
        var email = "test@example.com";
        var event = Event.builder().id(1L).nameEN("Sample Event").build();
        var shift =Shift.builder().event(event).build();
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(eventRepository.findById(event.getId())).thenReturn(Optional.of(event));

        emailUtil.sendJoinEventMail(email, shift);

        verify(javaMailSender, times(1)).send(mimeMessage);
    }
}
