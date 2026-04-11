package com.darcode.curalink.service.impl;

import com.darcode.curalink.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromAdress;

    @Override
    public void sendAppointmentReminderEmail(String to, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAdress);
        message.setTo(to);
        message.setSubject("Appointment Reminder");
        message.setText(body);
        mailSender.send(message);
        log.info("Email sent to {}", to);
    }
}
