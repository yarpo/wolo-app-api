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
          <a href="http://localhost:3000/verify-account?email=%s&otp=%s" target="_blank">click link to verify mail</a>
        </div>
        """, email,otp);
        mailSender(email, "WoloApp verify account", message, true);

    }

    public void sendResetPasswordEmail(String email) throws MessagingException {
        String message = String.format("""
                <html>
                <body style="font-family: 'Open Sans', sans-serif; margin: 20px;">
                    <div style="max-width: 600px; margin: 0 auto; text-align: center;">
                        <a href="http://localhost:3000/" style="text-decoration: none;" target="_blank">
                            <h2 style="font-size: 64px; color: #3769cb;">WoloApp</h2>
                        </a>
                        <h3 style="display: inline;">[EN] Forgot Password?</h3>
                        <p style="font-size: 16px; color: #666666; margin: 20px 30px;">User with your email has asked to change the password. Click the 'reset' button below to reset your password.</p>
                        <h3 style="display: inline;">[PL] Nie pamiętasz hasła?</h3>
                        <p style="font-size: 16px; color: #666666; margin: 20px 30px;">Użytkownik z Twoim adresem e-mail poprosił o zmianę hasła. Kliknij przycisk 'reset' poniżej, aby zresetować hasło.</p>
                        <h3 style="display: inline;">[RU] Забыли пароль?</h3>
                        <p style="font-size: 16px; color: #666666; margin: 20px 30px;">Пользователь с вашим адресом электронной почты запросил изменение пароля. Пожалуйста, нажмите кнопку 'reset', чтобы сбросить пароль.</p>
                        <h3 style="display: inline;">[UK] Забули пароль?</h3>
                        <p style="font-size: 16px; color: #666666; margin: 20px 30px;">Користувач з вашою електронною адресою попросив змінити пароль. Натисніть кнопку 'reset', щоб скинути пароль.</p>
                        <a href="http://localhost:3000/reset-password?email=%s" target="_blank" style="display: inline-block; background-color: #3769cb; color: #FFFFFF; font-size: 16px; font-weight: bold; text-align: center; text-decoration: none; padding: 10px 60px; border-radius: 4px; margin: 10px 0;">Reset</a>
                        <p style="font-size: 16px; color: #666666; margin: 10px;">If you did not request to reset your password, please ignore this email.</p>
                        <p style="font-size: 16px; color: #666666; margin: 10px;">Jeśli nie prosiłeś/aś o zresetowanie hasła, zignoruj tę wiadomość.</p>
                        <p style="font-size: 16px; color: #666666; margin: 10px;">Если вы не запрашивали сброс пароля, пожалуйста, проигнорируйте это сообщение.</p>
                        <p style="font-size: 16px; color: #666666; margin: 0 0 40px;">Якщо ви не запитували скидання пароля, будь ласка, проігноруйте цей лист.</p>
                    </div>
                </body>
                </html>
                """, email);
        mailSender(email, "[WoloApp] Forgot password?/Nie pamiętasz hasła?/Забыли пароль?/Забули пароль?", message, true);
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
        var mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
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
