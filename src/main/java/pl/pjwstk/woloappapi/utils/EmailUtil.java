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


    public void sendResetPasswordEmail(String email) throws MessagingException {
        String message = String.format("""
             <body style="font-family: 'Open Sans', sans-serif; background-color: #F2F2F2; display: flex; justify-content: center; align-items: flex-start; height: 100vh;">
                <div style="width: 100%%; display: flex; justify-content: center;">
                    <div style="max-width: 600px; margin: 0 auto; background-color: #FFFFFF; border-radius: 8px; box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.1); overflow: hidden; text-align: center;">
                        <div style="padding: 20px;">
                            <a href="http://localhost:3000/" target="_blank">
                                <img src="https://raw.githubusercontent.com/yarpo/wolo-app/e4cf379186c21a687389fc9755146fadbbef095c/src/images/logo.svg" alt="Woloapp" style="width: 400px; display: block; margin: 0 auto;">
                            </a>
                        </div>
                        <div style="padding: 20px 40px; text-align: center; border-bottom: 1px solid #aaaaaa; padding-bottom: 30px; margin-bottom: 20px;">
                            <div style="display: flex; justify-content: center; align-items: center; margin-bottom: 20px; gap: 10px;">
                                <svg width="32" height="32" viewBox="0 0 32 32"><rect x="1" y="4" width="30" height="24" rx="4" ry="4" fill="#071b65"></rect>
                                    <path d="M5.101,4h-.101c-1.981,0-3.615,1.444-3.933,3.334L26.899,28h.101c1.981,0,3.615-1.444,3.933-3.334L5.101,4Z" fill="#fff"></path>
                                    <path d="M22.25,19h-2.5l9.934,7.947c.387-.353,.704-.777,.929-1.257l-8.363-6.691Z" fill="#b92932"></path>
                                    <path d="M1.387,6.309l8.363,6.691h2.5L2.316,5.053c-.387,.353-.704,.777-.929,1.257Z" fill="#b92932"></path>
                                    <path d="M5,28h.101L30.933,7.334c-.318-1.891-1.952-3.334-3.933-3.334h-.101L1.067,24.666c.318,1.891,1.952,3.334,3.933,3.334Z" fill="#fff"></path>
                                    <rect x="13" y="4" width="6" height="24" fill="#fff"></rect><rect x="1" y="13" width="30" height="6" fill="#fff"></rect>
                                    <rect x="14" y="4" width="4" height="24" fill="#b92932"></rect><rect x="14" y="1" width="4" height="30" transform="translate(32) rotate(90)" fill="#b92932"></rect>
                                    <path d="M28.222,4.21l-9.222,7.376v1.414h.75l9.943-7.94c-.419-.384-.918-.671-1.471-.85Z" fill="#b92932"></path>
                                    <path d="M2.328,26.957c.414,.374,.904,.656,1.447,.832l9.225-7.38v-1.408h-.75L2.328,26.957Z" fill="#b92932"></path>
                                    <path d="M27,4H5c-2.209,0-4,1.791-4,4V24c0,2.209,1.791,4,4,4H27c2.209,0,4-1.791,4-4V8c0-2.209-1.791-4-4-4Zm3,20c0,1.654-1.346,3-3,3H5c-1.654,0-3-1.346-3-3V8c0-1.654,1.346-3,3-3H27c1.654,0,3,1.346,3,3V24Z" opacity=".15"></path>
                                    <path d="M27,5H5c-1.657,0-3,1.343-3,3v1c0-1.657,1.343-3,3-3H27c1.657,0,3,1.343,3,3v-1c0-1.657-1.343-3-3-3Z" fill="#fff" opacity=".2"></path>
                                </svg>
                                <h1 style="display: inline;">Forgot Password?</h1>
                            </div>
                            <p style="font-size: 16px; color: #666666; margin: 20px 0 0 0; padding: 0;">User with your email has asked to change the password. Please click the button below to reset your password.</p>
                            <a href="http://localhost:3000/reset-password?email=%s" target="_blank" style="display: inline-block; background-color: #3769cb; color: #FFFFFF; font-size: 16px; font-weight: bold; text-align: center; text-decoration: none; padding: 10px 20px; border-radius: 4px; margin: 20px 0;">Reset Password</a>
                            <p style="font-size: 16px; color: #666666; margin: 0; padding: 0;">If you did not request to reset your password, please ignore this email.</p>
                            <p style="font-size: 16px; color: #666666; margin: 0; padding: 0;margin-top: 20px; ">Thank you,<br>Woloapp Team</p>
                        </div>
                        <div style="padding: 20px 40px; text-align: center; border-bottom: 1px solid #aaaaaa; padding-bottom: 30px; margin-bottom: 20px;">
                            <div style="display: flex; justify-content: center; align-items: center; margin-bottom: 20px; gap: 10px;">
                                <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32">
                                    <path d="M1,24c0,2.209,1.791,4,4,4H27c2.209,0,4-1.791,4-4V15H1v9Z" fill="#cb2e40">
                                    </path>
                                    <path d="M27,4H5c-2.209,0-4,1.791-4,4v8H31V8c0-2.209-1.791-4-4-4Z" fill="#fff">
                                    </path>
                                    <path
                                        d="M5,28H27c2.209,0,4-1.791,4-4V8c0-2.209-1.791-4-4-4H5c-2.209,0-4,1.791-4,4V24c0,2.209,1.791,4,4,4ZM2,8c0-1.654,1.346-3,3-3H27c1.654,0,3,1.346,3,3V24c0,1.654-1.346,3-3,3H5c-1.654,0-3-1.346-3-3V8Z"
                                        opacity=".15"></path>
                                    <path
                                        d="M27,5H5c-1.657,0-3,1.343-3,3v1c0-1.657,1.343-3,3-3H27c1.657,0,3,1.343,3,3v-1c0-1.657-1.343-3-3-3Z"
                                        fill="#fff" opacity=".2"></path>
                                </svg>
                                <h1>Nie pamiętasz hasła?</h1>
                            </div>
                            <p style="font-size: 16px; color: #666666; margin: 20px 0 0 0; padding: 0;">Użytkownik z Twoim adresem e-mail poprosił o zmianę hasła. Kliknij przycisk poniżej, aby zresetować hasło.</p>
                            <a href="http://localhost:3000/reset-password?email=%s" target="_blank" style="display: inline-block; background-color: #3769cb; color: #FFFFFF; font-size: 16px; font-weight: bold; text-align: center; text-decoration: none; padding: 10px 20px; border-radius: 4px; margin: 20px 0;">Zresetuj hasło</a>
                            <p style="font-size: 16px; color: #666666; margin: 0; padding: 0;">Jeśli nie prosiłeś/aś o zresetowanie hasła, zignoruj tę wiadomość.</p>
                            <p style="font-size: 16px; color: #666666; margin: 0; padding: 0;margin-top: 20px; ">Dziękujemy,<br>Zespół Woloapp</p>
                        </div>
                        <div style="padding: 20px 40px; text-align: center; border-bottom: 1px solid #aaaaaa; padding-bottom: 30px; margin-bottom: 20px;">
                            <div style="display: flex; justify-content: center; align-items: center; margin-bottom: 20px; gap: 10px;">
                                <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32">
                                    <path fill="#1435a1" d="M1 11H31V21H1z"></path>
                                    <path d="M5,4H27c2.208,0,4,1.792,4,4v4H1v-4c0-2.208,1.792-4,4-4Z" fill="#fff">
                                    </path>
                                    <path d="M5,20H27c2.208,0,4,1.792,4,4v4H1v-4c0-2.208,1.792-4,4-4Z"
                                        transform="rotate(180 16 24)" fill="#c53a28"></path>
                                    <path
                                        d="M27,4H5c-2.209,0-4,1.791-4,4V24c0,2.209,1.791,4,4,4H27c2.209,0,4-1.791,4-4V8c0-2.209-1.791-4-4-4Zm3,20c0,1.654-1.346,3-3,3H5c-1.654,0-3-1.346-3-3V8c0-1.654,1.346-3,3-3H27c1.654,0,3,1.346,3,3V24Z"
                                        opacity=".15"></path>
                                    <path
                                        d="M27,5H5c-1.657,0-3,1.343-3,3v1c0-1.657,1.343-3,3-3H27c1.657,0,3,1.343,3,3v-1c0-1.657-1.343-3-3-3Z"
                                        fill="#fff" opacity=".2"></path>
                                </svg>
                                <h1>Забыли пароль?</h1>
                            </div>
                            <p style="font-size: 16px; color: #666666; margin: 20px 0 0 0; padding: 0;">Пользователь с вашим адресом электронной почты запросил изменение пароля. Пожалуйста, нажмите кнопку ниже, чтобы сбросить пароль.</p>
                            <a href="http://localhost:3000/reset-password?email=%s" target="_blank" style="display: inline-block; background-color: #3769cb; color: #FFFFFF; font-size: 16px; font-weight: bold; text-align: center; text-decoration: none; padding: 10px 20px; border-radius: 4px; margin: 20px 0;">Сбросить пароль</a>
                            <p style="font-size: 16px; color: #666666; margin: 0; padding: 0;">Если вы не запрашивали сброс пароля, пожалуйста, проигнорируйте это сообщение.</p>
                            <p style="font-size: 16px; color: #666666; margin: 0; padding: 0;margin-top: 20px; ">Спасибо,<br>Команда Woloapp</p>
                        </div>
                        <div style="padding: 20px 40px; text-align: center; border-bottom: 1px solid #aaaaaa; padding-bottom: 30px; margin-bottom: 20px;">
                            <div style="display: flex; justify-content: center; align-items: center; margin-bottom: 20px; gap: 10px;">
                                <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32">
                                    <path d="M31,8c0-2.209-1.791-4-4-4H5c-2.209,0-4,1.791-4,4v9H31V8Z" fill="#2455b2">
                                    </path>
                                    <path d="M5,28H27c2.209,0,4-1.791,4-4v-8H1v8c0,2.209,1.791,4,4,4Z" fill="#f9da49">
                                    </path>
                                    <path
                                        d="M5,28H27c2.209,0,4-1.791,4-4V8c0-2.209-1.791-4-4-4H5c-2.209,0-4,1.791-4,4V24c0,2.209,1.791,4,4,4ZM2,8c0-1.654,1.346-3,3-3H27c1.654,0,3,1.346,3,3V24c0,1.654-1.346,3-3,3H5c-1.654,0-3-1.346-3-3V8Z"
                                        opacity=".15"></path>
                                    <path
                                        d="M27,5H5c-1.657,0-3,1.343-3,3v1c0-1.657,1.343-3,3-3H27c1.657,0,3,1.343,3,3v-1c0-1.657-1.343-3-3-3Z"
                                        fill="#fff" opacity=".2"></path>
                                </svg>
                                <h1>Забули пароль?</h1>
                            </div>
                            <p style="font-size: 16px; color: #666666; margin: 20px 0 0 0; padding: 0;">Користувач з вашою електронною адресою попросив змінити пароль. Натисніть кнопку нижче, щоб скинути пароль.</p>
                            <a href="http://localhost:3000/reset-password?email=%s" target="_blank" style="display: inline-block; background-color: #3769cb; color: #FFFFFF; font-size: 16px; font-weight: bold; text-align: center; text-decoration: none; padding: 10px 20px; border-radius: 4px; margin: 20px 0;">Скинути пароль</a>
                            <p style="font-size: 16px; color: #666666; margin: 0; padding: 0;">Якщо ви не запитували скидання пароля, будь ласка, проігноруйте цей лист.</p>
                            <p style="font-size: 16px; color: #666666; margin: 0; padding: 0;margin-top: 20px; ">Дякуємо,<br>Команда Woloapp</p>
                        </div>
                    </div>
                </div>
            </body>
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
