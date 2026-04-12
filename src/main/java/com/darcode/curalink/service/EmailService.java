package com.darcode.curalink.service;

import java.util.Map;

public interface EmailService {
    void sendAppointmentReminderEmail(String to, String templateName, Map<String, Object> variables);

//    void sendAppointmentConfirmationEmail(String to, String Subject, String body);
}
