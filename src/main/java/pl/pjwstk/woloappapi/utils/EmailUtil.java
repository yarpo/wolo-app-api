package pl.pjwstk.woloappapi.utils;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import pl.pjwstk.woloappapi.model.entities.Event;
import pl.pjwstk.woloappapi.model.entities.Shift;
import pl.pjwstk.woloappapi.repository.EventRepository;

@Component
@RequiredArgsConstructor
public class EmailUtil {
    private final JavaMailSender javaMailSender;
    private final EventRepository eventRepository;

    public void sendOtpMail(String email, String otp) throws MessagingException{
        String message = String.format("""
        <div>
          <a href="http://localhost:8080/auth/verify-account?email=%s&otp=%s" target="_blank">click link to verify mail</a>
        </div>
        """, email,otp);
        mailSender(email, "Set Password", message, true);

    }

    public void sendResetPasswordEmail(String email) throws MessagingException {
        String message = String.format("""
        <div>
          <a href="http://localhost:3000/reset-password?email=%s" target="_blank">click link to set password</a>
        </div>
        """, email);
        mailSender(email, "Set Password", message, true);
    }

    public void sendDeleteEventMessage(String email, Long id) throws MessagingException{
        String message = String.format("%s has been cancelled",
                eventRepository
                .findById(id).map(Event::getNameEN)
                .orElse("The event you signed up for "));
        mailSender(email, "The event you signed up for has been cancelled", message, false);
    }

    public void sendEditEventMail(String email, Long id) throws MessagingException{
        String message = String.format("%s has been changed",
                eventRepository
                        .findById(id).map(Event::getNameEN)
                        .orElse("The event you signed up for "));
        mailSender(email, "The event you signed up for has been changed", message, false);
    }

    public void sendPeselVerificationEmail(String email, Long id) throws MessagingException {
        String message = String.format("If you want to participate in %s, you must verify your PESEL " +
                "and re-register for this event",
                eventRepository
                        .findById(id).map(Event::getNameEN)
                        .orElse("event "));
        mailSender(email, "The event requirements you signed up for has been changed", message, false);
    }

    public void sendAgreementNeededEmail(String email, Long id) throws MessagingException{
        String message = String.format("If you want to participate in %s, "+
                "you must sign a volunteer agreement and re-register for this event",
                eventRepository
                        .findById(id).map(Event::getNameEN)
                        .orElse("event "));
        mailSender(email, "The event requirements you signed up for has been changed", message, false);
    }

    public void sendUpdateCapacityEmail(String email, Long id) throws MessagingException{
        String message = String.format("Unfortunately, the number of participants in %s has been reduced. " +
                        "You can no longer take part in it. Sorry for the inconvenience. " +
                        "We invite you to our application where you can find others interesting events for you",
                eventRepository
                        .findById(id).map(Event::getNameEN)
                        .orElse("event "));
        mailSender(email, "Important event update", message, false);
    }

    public void sendMinAgeMail(String email, Long id) throws MessagingException{
        String message = String.format("Unfortunately, the minimum age required for %s has been changed. " +
                        "You can no longer take part in it. Sorry for the inconvenience. " +
                        "We invite you to our application where you can find others interesting events for you",
                eventRepository
                        .findById(id).map(Event::getNameEN)
                        .orElse("event "));
        mailSender(email, "Important event update", message, false);
    }

    public void mailSender(String email, String subject, String message, boolean isHtml) throws MessagingException{
        var mimeMessage = javaMailSender.createMimeMessage();
        var mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(message, isHtml);
        javaMailSender.send(mimeMessage);
    }

    public void sendJoinEventMail(String email, Shift shift) throws MessagingException {
        String message = String.format("Нou are automatically registered for the %s because you were on the reserve list. " +
                "If you are no longer interested in this event, please log in to your account and unsubscribe from the event.",
                eventRepository
                        .findById(shift.getEvent().getId()).map(Event::getNameEN)
                        .orElse("event"));
        mailSender(email, "You are registered for the event!", message, false);
    }
}
