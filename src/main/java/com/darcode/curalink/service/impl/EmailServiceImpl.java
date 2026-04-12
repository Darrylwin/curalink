package com.darcode.curalink.service.impl;

import com.darcode.curalink.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromAdress;

    @Override
    public void sendAppointmentReminderEmail(
            String to, Map<String, Object> variables
    ) {
        sendEmail(
                to,
                "emails/appointment-reminder",
                variables,
                "Appointment Reminder"
        );
    }

    @Override
    public void sendAppointmentConfirmationEmail(String to, Map<String, Object> variables) {
        sendEmail(
                to,
                "emails/appointment-confirmation",
                variables,
                "Appointment Confirmation"
        );
    }

    // ----------- Helper methods ---------------
    private void sendEmail(String to, String templateName, Map<String, Object> variables, String subject) {
        try {
            Context context = new Context();
            context.setVariables(variables);

            String htmlContent = templateEngine.process(templateName, context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromAdress);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            log.debug("Sending email to: {}", to);
            mailSender.send(message);
            log.info("Email sent to {}", to);
        } catch (MessagingException exception) {
            log.error("Failed send email to {} : {}", to, exception.getMessage());
        }
    }
}
