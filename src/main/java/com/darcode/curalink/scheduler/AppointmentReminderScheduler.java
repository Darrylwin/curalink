package com.darcode.curalink.scheduler;

import com.darcode.curalink.model.Appointment;
import com.darcode.curalink.service.AppointmentService;
import com.darcode.curalink.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppointmentReminderScheduler {

    private final AppointmentService appointmentService;
    private final EmailService emailService;

    @Scheduled(cron = "0 0 * * * *")
    public void sendAppointmentReminder() {
        log.info("Begining of appointment reminder email sending");

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime in24Hours = now.plusHours(24);

        try {
            // find all appointments that will start in 24 h
            List<Appointment> appointmentsIn24Hours = appointmentService.findAllAppointmentsBetween(now, in24Hours);
            log.debug("Appointments starting in 24 hours: {}", appointmentsIn24Hours.size());

            for (Appointment appointment : appointmentsIn24Hours) {
                try {
                    String patientEmail = appointment.getPatient().getEmail();
                    String reminderHtml = buildReminderHtml(appointment);

                    log.debug("Sending email to: {}", patientEmail);
                    emailService.sendAppointmentReminderEmail(patientEmail, reminderHtml);
                    log.info("Reminder email sent for appointment {}", appointment.getId());
                } catch (Exception e) {
                    log.error("Error sending email for appointment {}", appointment.getId(), e);
                }
            }

            log.info("Appointment Reminder email sending completed");
        } catch (Exception e) {
            log.error("Error in appointment reminder scheduler", e);
        }
    }

    // -------------- Utility methods ------------------
    private String buildReminderHtml(Appointment appointment) {
        return """
                        <h2>Rappel de rendez-vous</h2>
                        <p>Bonjour %s,</p>
                        <p>Vous avez un rendez-vous demain avec le Dr. %s à %s.</p>
                """.formatted(
                appointment.getPatient().getFirstName(),
                appointment.getDoctor().getFirstName(),
                appointment.getTimeSlot().getStartTime()
        );
    }
}
