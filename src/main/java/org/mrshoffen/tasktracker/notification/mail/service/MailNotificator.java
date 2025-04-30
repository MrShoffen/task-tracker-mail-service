package org.mrshoffen.tasktracker.notification.mail.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailNotificator {

    private final JavaMailSender mailSender;

    @Value("${mail-user.from}")
    private String fromUser;

    @SneakyThrows
    public void send(String to, String subject, String text) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromUser, "TaskZone");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);

        try {
            mailSender.send(message);
            log.info("Message successfully sent: {} - {}", subject, to);
        } catch (Exception e) {
            log.warn("Failed to send message: {}", e.getMessage());
        }
    }

}
