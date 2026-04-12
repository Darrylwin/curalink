package com.darcode.curalink.scheduler;

import com.darcode.curalink.model.Appointment;
import com.darcode.curalink.service.AppointmentService;
import com.darcode.curalink.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                Map<String, Object> variables = new HashMap<>();
                variables.put("patientName", appointment.getPatient().getFirstName());
                variables.put("doctorName", appointment.getDoctor().getFirstName());
                variables.put("appointmentDate", appointment.getTimeSlot().getStartTime().toLocalDate());
                variables.put("appointmentTime", appointment.getTimeSlot().getStartTime().toLocalTime());

                emailService.sendAppointmentReminderEmail(
                        appointment.getPatient().getEmail(),
                        "emails/appointment-reminder",
                        variables
                );
            }

            log.info("Appointment Reminder email sending completed");
        } catch (Exception e) {
            log.error("Error in appointment reminder scheduler", e);
        }
    }
}
