package com.darcode.curalink.service;

public interface EmailService {
    void sendAppointmentReminderEmail(String to, String subject, String body);

//    void sendAppointmentConfirmationEmail(String to, String Subject, String body);
}
