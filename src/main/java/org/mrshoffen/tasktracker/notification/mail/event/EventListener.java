package org.mrshoffen.tasktracker.notification.mail.event;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrshoffen.tasktracker.commons.kafka.event.authentication.AuthenticationSuccessfulEvent;
import org.mrshoffen.tasktracker.commons.kafka.event.registration.RegistrationAttemptEvent;
import org.mrshoffen.tasktracker.commons.kafka.event.registration.RegistrationSuccessfulEvent;
import org.mrshoffen.tasktracker.notification.mail.model.Mail;
import org.mrshoffen.tasktracker.notification.mail.service.MailBodyService;
import org.mrshoffen.tasktracker.notification.mail.service.MailNotificatorService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventListener {

    private final MailBodyService mailBodyService;

    private final MailNotificatorService mailNotificatorService;


    @KafkaListener(topics = RegistrationAttemptEvent.TOPIC)
    public void handleRegistrationAttempt(RegistrationAttemptEvent event) {
        log.info("Received event in topic {} - {}", RegistrationAttemptEvent.TOPIC, event);
        Mail confirmation = mailBodyService.buildConfirmationMessage(event);
        mailNotificatorService.send(confirmation);
    }

    @KafkaListener(topics = RegistrationSuccessfulEvent.TOPIC)
    public void handleRegistrationSuccess(RegistrationSuccessfulEvent event) {
        log.info("Received event in topic {} - {}", RegistrationSuccessfulEvent.TOPIC, event);

        Mail greetings = mailBodyService.buildGreetingsMessage(event);
        mailNotificatorService.send(greetings);
    }

    @KafkaListener(topics = AuthenticationSuccessfulEvent.TOPIC)
    public void handleAuthenticationSuccess(AuthenticationSuccessfulEvent event) {
        log.info("Received event in topic {} - {}", AuthenticationSuccessfulEvent.TOPIC, event);

        Mail auth = mailBodyService.buildAuthenticationMessage(event);
        mailNotificatorService.send(auth);
    }

}
