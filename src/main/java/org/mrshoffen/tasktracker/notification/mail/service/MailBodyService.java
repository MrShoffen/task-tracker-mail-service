package org.mrshoffen.tasktracker.notification.mail.service;

import lombok.RequiredArgsConstructor;
import org.mrshoffen.tasktracker.commons.kafka.event.authentication.AuthenticationSuccessfulEvent;
import org.mrshoffen.tasktracker.commons.kafka.event.creds.EmailUpdateAttemptEvent;
import org.mrshoffen.tasktracker.commons.kafka.event.registration.RegistrationAttemptEvent;
import org.mrshoffen.tasktracker.commons.kafka.event.registration.RegistrationSuccessfulEvent;
import org.mrshoffen.tasktracker.notification.mail.model.Mail;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class MailBodyService {

    private final TemplateEngine templateEngine;

    public Mail buildConfirmationMessage(RegistrationAttemptEvent event) {
        Context context = new Context();
        context.setVariable("name", event.getEmail());
        context.setVariable("confirmLink", event.getConfirmationLink());
        String body = templateEngine.process("confirmation-registration-template", context);

        return new Mail(event.getEmail(), "Подтвердите почту", body);
    }


    public Mail buildGreetingsMessage(RegistrationSuccessfulEvent event) {
        String body = templateEngine.process("greetings-template", new Context());
        return new Mail(event.getEmail(), "Добро пожаловать", body);
    }

    public Mail buildAuthenticationMessage(AuthenticationSuccessfulEvent event) {
        Context context = new Context();
        context.setVariable("ipAddr", event.getIpAddr());
        String body = templateEngine.process("authentication-template", context);

        return new Mail(event.getEmail(), "Уведомление о входе", body);
    }

    public Mail buildMailConfirmationWithCode(EmailUpdateAttemptEvent event) {
        Context context = new Context();
        context.setVariable("code", event.getConfirmationCode());
        String body = templateEngine.process("confirmation-email-change-template", context);

        return new Mail(event.getNewEmail(), "Подтвердите новую почту", body);
    }
}
