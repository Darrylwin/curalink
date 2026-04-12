package com.darcode.curalink.service;

import java.util.Map;

public interface EmailService {
    void sendAppointmentReminderEmail(String to, Map<String, Object> variables);

    void sendAppointmentConfirmationEmail(String to, Map<String, Object> variables);
}
