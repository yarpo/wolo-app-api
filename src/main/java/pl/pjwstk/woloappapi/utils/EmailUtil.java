package pl.pjwstk.woloappapi.utils;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import pl.pjwstk.woloappapi.model.entities.Event;
import pl.pjwstk.woloappapi.repository.EventRepository;

@Component
@RequiredArgsConstructor
public class EmailUtil {
    private final JavaMailSender javaMailSender;
    private final EventRepository eventRepository;


    public void sendResetPasswordEmail(String email) throws MessagingException {
        mailSender(email, null, "Set Password", """
        <div>
          <a href="http://localhost:8080/reset-password?email=%s" target="_blank">click link to set password</a>
        </div>
        """);
    }

    public void sendDeleteEventMessage(String email, Long id) throws MessagingException{
        mailSender(email, id, "The event you signed up for has been cancelled",
                "%s has been cancelled");
    }

    public void sendEditEventMail(String email, Long id) throws MessagingException{
        mailSender(email, id, "The event you signed up for has been changed", "%s has been changed");
    }

    public void sendPeselVerificationEmail(String email, Long id) throws MessagingException {
        mailSender(email, id, "The event requirements you signed up for has been changed",
                "If you want to participate in %s, you must verify your PESEL and re-register for this event");
    }

    public void sendAgreementNeededEmail(String email, Long id) throws MessagingException{
        mailSender(email, id, "The event requirements you signed up for has been changed",
                "If you want to participate in %s, you must sign a volunteer agreement " +
                        "and re-register for this event");
    }

    public void sendUpdateCapacityEmail(String email, Long id) throws MessagingException{
        mailSender(email, id, "Important event update",
                "Unfortunately, the number of participants in %s has been reduced. " +
                "You can no longer take part in it. Sorry for the inconvenience. " +
                "We invite you to our application where you can find others interesting events for you");
    }

    public void sendMinAgeMail(String email, Long id) throws MessagingException{
        mailSender(email, id, "Important event update",
                "Unfortunately, the minimum age required for %s has been changed. " +
                "You can no longer take part in it. Sorry for the inconvenience. " +
                "We invite you to our application where you can find others interesting events for you");
    }

    public void mailSender(String email, Long id, String subject, String message) throws MessagingException{
        var mimeMessage = javaMailSender.createMimeMessage();
        var mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject(subject);
        String eventName = (id != null) ?
                eventRepository.findById(id).map(Event::getNameEN).orElse("event") : email;
        mimeMessageHelper.setText(message.formatted(eventName));
        javaMailSender.send(mimeMessage);
    }
}
