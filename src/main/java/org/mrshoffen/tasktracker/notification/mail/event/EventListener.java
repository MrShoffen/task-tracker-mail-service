package org.mrshoffen.tasktracker.notification.mail.event;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrshoffen.tasktracker.commons.kafka.event.registration.RegistrationAttemptEvent;
import org.mrshoffen.tasktracker.commons.kafka.event.registration.RegistrationSuccessfulEvent;
import org.mrshoffen.tasktracker.notification.mail.service.MailBodyService;
import org.mrshoffen.tasktracker.notification.mail.service.MailNotificator;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventListener {

    private final MailBodyService mailBodyService;

    private final MailNotificator mailNotificator;


    @KafkaListener(topics = RegistrationAttemptEvent.TOPIC)
    public void handleRegistrationAttempt(RegistrationAttemptEvent event) {
        log.info("Received event in topic {} - {}", RegistrationAttemptEvent.TOPIC, event);
        String confirmation = mailBodyService.buildConfirmationMessage(event);
        mailNotificator.send(event.getEmail(), "Подтвердите почту", confirmation);
    }

    @KafkaListener(topics = RegistrationSuccessfulEvent.TOPIC)
    public void handleRegistrationSuccess(RegistrationSuccessfulEvent event) {
        log.info("Received event in topic {} - {}", RegistrationSuccessfulEvent.TOPIC, event);

        String greetings = mailBodyService.buildGreetingsMessage(); //todo add mail to succesfull event
        mailNotificator.send(event.getEmail(), "Приветственное сообщение", greetings);
    }

}
