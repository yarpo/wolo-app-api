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
        var mimeMessage = javaMailSender.createMimeMessage();
        var mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Set Password");
        mimeMessageHelper.setText("""
        <div>
          <a href="http://localhost:8080/reset-password?email=%s" target="_blank">click link to set password</a>
        </div>
        """.formatted(email), true);

        javaMailSender.send(mimeMessage);
    }

    public void sendDeleteEventMessage(String email, Long id) throws MessagingException{
        var mimeMessage = javaMailSender.createMimeMessage();
        var mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Еhe event you signed up for has been cancelled");
        mimeMessageHelper.setText("%s has been cancelled".formatted(eventRepository
                .findById(id).map(Event::getNameEN)
                .orElse("Еhe event you signed up for ")));
        javaMailSender.send(mimeMessage);
    }

    public void sendEditEventMail(String email, Long id) throws MessagingException{
        var mimeMessage = javaMailSender.createMimeMessage();
        var mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Еhe event you signed up for has been changed");
        mimeMessageHelper.setText("%s has been changed".formatted(eventRepository
                .findById(id).map(Event::getNameEN)
                .orElse("Еhe event you signed up for ")));
        javaMailSender.send(mimeMessage);
    }

    public void sendPeselVerificationEmail(String email, Long id) throws MessagingException {
        var mimeMessage = javaMailSender.createMimeMessage();
        var mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Еhe event requirements you signed up for has been changed");
        mimeMessageHelper.setText("If you want to participate in %s, you must verify your PESEL and re-register for this event"
                .formatted(eventRepository
                    .findById(id).map(Event::getNameEN)
                    .orElse("event ")));
        javaMailSender.send(mimeMessage);
    }

    public void sendAgreementNeededEmail(String email, Long id) throws MessagingException{
        var mimeMessage = javaMailSender.createMimeMessage();
        var mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Еhe event requirements you signed up for has been changed");
        mimeMessageHelper.setText("If you want to participate in %s, you must sign a volunteer agreement and re-register for this event"
                .formatted(eventRepository
                        .findById(id).map(Event::getNameEN)
                        .orElse("event ")));
        javaMailSender.send(mimeMessage);

    }

    public void sendUpdateCapacityEmail(String email, Long id) throws MessagingException{
        var mimeMessage = javaMailSender.createMimeMessage();
        var mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Important event update");
        mimeMessageHelper.setText(("Unfortunately, the number of participants in %s has been reduced. " +
                "You can no longer take part in it. Sorry for the inconvenience. " +
                "We invite you to our application where you can find others interesting events for you")
                .formatted(eventRepository
                        .findById(id).map(Event::getNameEN)
                        .orElse("event ")));
        javaMailSender.send(mimeMessage);
    }
}
