package pl.pjwstk.woloappapi.utils;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import pl.pjwstk.woloappapi.model.entities.Event;
import pl.pjwstk.woloappapi.model.entities.Shift;
import pl.pjwstk.woloappapi.repository.EventRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EmailUtil {
    private final JavaMailSender javaMailSender;
    private final EventRepository eventRepository;

    public void sendOtpMail(String email, String otp) throws MessagingException{
        String message = String.format("""
                        <html>
                                  <body style="font-family: 'Open Sans', sans-serif; margin: 20px;">
                                      <div style="max-width: 600px; margin: 0 auto; text-align: center;">
                                          <a href="http://localhost:3000/" style="text-decoration: none;" target="_blank">
                                              <h2 style="font-size: 64px; color: #3769cb;">WoloApp</h2>
                                          </a>
                                          <h3 style="display: inline;">[EN] Click the button to verify your email</h3>
                                          <p style="font-size: 16px; color: #666666; margin: 20px 30px;">Click the 'verify' button below to verify your email address.</p>
                                          <h3 style="display: inline;">[PL] Kliknij przycisk, aby zweryfikować swój adres e-mail</h3>
                                          <p style="font-size: 16px; color: #666666; margin: 20px 30px;">Kliknij przycisk 'verify' poniżej, aby zweryfikować swój adres e-mail.</p>
                                          <h3 style="display: inline;">[RU] Нажмите кнопку, чтобы подтвердить ваш электронный адрес.</h3>
                                          <p style="font-size: 16px; color: #666666; margin: 20px 30px;">Нажмите кнопку 'verify' ниже, чтобы подтвердить свой адрес электронной почты.</p>
                                          <h3 style="display: inline;">[UA] Натисніть кнопку, щоб підтвердити свою електронну адресу</h3>
                                          <p style="font-size: 16px; color: #666666; margin: 20px 30px;">Клацніть кнопку 'verify' нижче, щоб підтвердити вашу електронну адресу.</p>
                                          <a href="http://localhost:3000/verify-account?email=%s&otp=%s" target="_blank" style="display: inline-block; background-color: #3769cb; color: #FFFFFF; font-size: 16px; font-weight: bold; text-align: center; text-decoration: none; padding: 10px 60px; border-radius: 4px; margin: 10px 0;">Verify</a>
                                          <p style="font-size: 16px; color: #666666; margin: 10px;">If you did not request to verify your email, please ignore this email.</p>
                                          <p style="font-size: 16px; color: #666666; margin: 10px;">Jeśli nie prosiłeś/aś o weryfikację swojego adresu e-mail, zignoruj tę wiadomość.</p>
                                          <p style="font-size: 16px; color: #666666; margin: 10px;">Если вы не запрашивали подтверждение своего адреса электронной почты, пожалуйста, проигнорируйте это сообщение.</p>
                                          <p style="font-size: 16px; color: #666666; margin: 0 0 40px;">Якщо ви не запитували підтвердження вашої електронної адреси, будь ласка, проігноруйте цей лист.</p>
                                      </div>
                                  </body>
                              </html>
                """, email,otp);
        mailSender(email, "[WoloApp] Email Verification / Weryfikacja email / Верификация электронной почты / Верифікація електронної пошти", message, true);

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
                        <h3 style="display: inline;">[UA] Забули пароль?</h3>
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
        mailSender(email, "[WoloApp] Forgot password? / Nie pamiętasz hasła? / Забыли пароль?/ Забули пароль?", message, true);
    }

    public void sendDeleteEventMessage(String email, Long id) throws MessagingException{
        Optional<String> eventNameEN = eventRepository.findById(id).map(Event::getNameEN);
        Optional<String> eventNamePL = eventRepository.findById(id).map(Event::getNamePL);
        Optional<String> eventNameRU = eventRepository.findById(id).map(Event::getNameRU);
        Optional<String> eventNameUA = eventRepository.findById(id).map(Event::getNameUA);
        String message = String.format("""
                        <html>
                            <body style="font-family: 'Open Sans', sans-serif; margin: 20px;">
                                <div style="max-width: 600px; margin: 0 auto; text-align: center;">
                                    <a href="http://localhost:3000/" style="text-decoration: none;" target="_blank">
                                        <h2 style="font-size: 64px; color: #3769cb;">WoloApp</h2>
                                    </a>
                                    <h3 style="display: inline;">[EN] The event %s has been canceled</h3>
                                    <p style="font-size: 16px; color: #666666; margin: 20px 30px;">We're sorry to let you know that the event you signed up for has been canceled. Feel free to check out other events you might like instead! </p>
                                    <h3 style="display: inline;">[PL] Wydarzenie %s zostało odwołane</h3>
                                    <p style="font-size: 16px; color: #666666; margin: 20px 30px;">Z przykrością informujemy, że wydarzenie na które się zapisałeś, zostało odwołane. Zapraszamy do zapoznania się z innymi wydarzeniami, które mogą Cię zainteresować!</p>
                                    <h3 style="display: inline;">[RU] Событие %s отменено</h3>
                                    <p style="font-size: 16px; color: #666666; margin: 20px 30px;">Мы с сожалением сообщаем вам, что мероприятие, на которое вы зарегистрировались, было отменено. Пожалуйста, ознакомьтесь с другими мероприятиями, которые могут вас заинтересовать!</p>
                                    <h3 style="display: inline;">[UA] Подія %s скасована</h3>
                                    <p style="font-size: 16px; color: #666666; margin: 20px 30px;">Ми сповіщаємо вас, що подія, на яку ви зареєструвалися, скасована. Запрошуємо ознайомитися з іншими подіями, які можуть вас зацікавити!</p>
                                </div>
                            </body>
                            </html>
                        
                        """,
                eventNameEN.orElse(""),
                eventNamePL.orElse(""),
                eventNameRU.orElse(""),
                eventNameUA.orElse(""));
        mailSender(email, "[WoloApp] Event canceled / Wydarzenie odwołane / Отменено событие / Скасовано подія", message, true);
    }

    public void sendEditEventMail(String email, Long id) throws MessagingException{
        Optional<String> eventNameEN = eventRepository.findById(id).map(Event::getNameEN);
        Optional<String> eventNamePL = eventRepository.findById(id).map(Event::getNamePL);
        Optional<String> eventNameRU = eventRepository.findById(id).map(Event::getNameRU);
        Optional<String> eventNameUA = eventRepository.findById(id).map(Event::getNameUA);
        String message = String.format("""
                        <html>
                            <body style="font-family: 'Open Sans', sans-serif; margin: 20px;">
                                <div style="max-width: 600px; margin: 0 auto; text-align: center;">
                                    <a href="http://localhost:3000/" style="text-decoration: none;" target="_blank">
                                        <h2 style="font-size: 64px; color: #3769cb;">WoloApp</h2>
                                    </a>
                                    <h3 style="display: inline;">[EN] The event %s has changed</h3>
                                    <p style="font-size: 16px; color: #666666; margin: 20px 30px;">We inform you that the details of the event you signed up for have changed. Visit our website to review the updated information.</p>
                                    <h3 style="display: inline;">[PL] Wydarzenie %s zostało zmienione</h3>
                                    <p style="font-size: 16px; color: #666666; margin: 20px 30px;">Informujemy, że szczegóły wydarzenia, na które się zapisałeś, zostały zmienione. Odwiedź naszą stronę, aby zapoznać się z zaktualizowanymi informacjami.</p>
                                    <h3 style="display: inline;">[RU] Событие %s изменилось</h3>
                                    <p style="font-size: 16px; color: #666666; margin: 20px 30px;">Мы сообщаем вам, что детали мероприятия, на которое вы зарегистрировались, изменились. Посетите наш веб-сайт, чтобы ознакомиться с обновленной информацией.</p>
                                    <h3 style="display: inline;">[UA] Подія %s змінилася</h3>
                                    <p style="font-size: 16px; color: #666666; margin: 20px 30px;">Ми повідомляємо вас, що деталі події, на яку ви зареєструвалися, змінилися. Відвідайте наш веб-сайт, щоб ознайомитися з оновленою інформацією.</p>
                                </div>
                            </body>
                        </html>""",
                eventNameEN.orElse(""),
                eventNamePL.orElse(""),
                eventNameRU.orElse(""),
                eventNameUA.orElse(""));
        mailSender(email, "[WoloApp] Event changes / Zmiany wydarzenia / Изменения в мероприятии / Зміни у події ", message, true);
    }

    public void sendPeselVerificationEmail(String email, Long id) throws MessagingException {
        Optional<String> eventNameEN = eventRepository.findById(id).map(Event::getNameEN);
        Optional<String> eventNamePL = eventRepository.findById(id).map(Event::getNamePL);
        Optional<String> eventNameRU = eventRepository.findById(id).map(Event::getNameRU);
        Optional<String> eventNameUA = eventRepository.findById(id).map(Event::getNameUA);

        String message = String.format("If you want to participate in %s, you must verify your PESEL " +
                "and re-register for this event",
                eventNameEN.orElse(""),
                eventNamePL.orElse(""),
                eventNameRU.orElse(""),
                eventNameUA.orElse(""));
        mailSender(email, "[WoloApp] Event requirements change / Zmiany wymagań wydarzenia", message, false);
    }

    public void sendAgreementNeededEmail(String email, Long id) throws MessagingException{
        Optional<String> eventNameEN = eventRepository.findById(id).map(Event::getNameEN);
        Optional<String> eventNamePL = eventRepository.findById(id).map(Event::getNamePL);
        Optional<String> eventNameRU = eventRepository.findById(id).map(Event::getNameRU);
        Optional<String> eventNameUA = eventRepository.findById(id).map(Event::getNameUA);
        String message = String.format("If you want to participate in %s, "+
                "you must sign a volunteer agreement and re-register for this event",
                eventNameEN.orElse("event"),
                eventNamePL.orElse("wydarzeniu"),
                eventNameRU.orElse(""),
                eventNameUA.orElse(""));
        mailSender(email, "[WoloApp] Event requirements change /", message, false);
    }

    public void sendUpdateCapacityEmail(String email, Long id) throws MessagingException{
        String message = String.format("Unfortunately, the number of participants in %s has been reduced. " +
                        "You can no longer take part in it. Sorry for the inconvenience. " +
                        "We invite you to our application where you can find others interesting events for you",
                eventRepository
                        .findById(id).map(Event::getNameEN)
                        .orElse("event "));
        mailSender(email, "[WoloApp] Important event change /", message, false);
    }

    public void sendMinAgeMail(String email, Long id) throws MessagingException{
        Optional<String> eventNameEN = eventRepository.findById(id).map(Event::getNameEN);
        Optional<String> eventNamePL = eventRepository.findById(id).map(Event::getNamePL);
        Optional<String> eventNameRU = eventRepository.findById(id).map(Event::getNameRU);
        Optional<String> eventNameUA = eventRepository.findById(id).map(Event::getNameUA);
        String message = String.format("Unfortunately, the minimum age required for %s has been changed. " +
                        "You can no longer take part in it. Sorry for the inconvenience. " +
                        "We invite you to our application where you can find others interesting events for you",
                eventNameEN.orElse("event"),
                eventNamePL.orElse("wydarzeniu"),
                eventNameRU.orElse(""),
                eventNameUA.orElse(""));
        mailSender(email, "[WoloApp] Important event change /", message, false);
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
        Optional<String> eventNameEN =eventRepository
                .findById(shift.getEvent().getId()).map(Event::getNameEN);
        Optional<String> eventNamePL =eventRepository
                .findById(shift.getEvent().getId()).map(Event::getNameEN);
        Optional<String> eventNameRU = eventRepository
                .findById(shift.getEvent().getId()).map(Event::getNameEN);
        Optional<String> eventNameUA = eventRepository
                .findById(shift.getEvent().getId()).map(Event::getNameEN);

        String message = String.format("Нou are automatically registered for the %s because you were on the reserve list. " +
                "If you are no longer interested in this event, please log in to your account and unsubscribe from the event.",
                eventNameEN.orElse("event"),
                eventNamePL.orElse("wydarzenie"),
                eventNameRU.orElse(""),
                eventNameUA.orElse(""));
        mailSender(email, "[WoloApp] Event Registration Confirmed! /", message, false);
    }
}
