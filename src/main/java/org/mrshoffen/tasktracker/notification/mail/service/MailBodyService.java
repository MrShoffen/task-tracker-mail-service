package org.mrshoffen.tasktracker.notification.mail.service;

import lombok.RequiredArgsConstructor;
import org.mrshoffen.tasktracker.commons.kafka.event.registration.RegistrationAttemptEvent;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class MailBodyService {

    private final TemplateEngine templateEngine;

    public String buildConfirmationMessage(RegistrationAttemptEvent event) {
        Context context = new Context();
        context.setVariable("name", event.getEmail());
        context.setVariable("confirmLink", "http://localhost:8080/api/v1/auth/sign-up?confirm=" + event.getRegistrationId());

        return templateEngine.process("confirmation-template", context);
    }


    public String buildGreetingsMessage() {
        return templateEngine.process("greetings-template", new Context());
    }
}
