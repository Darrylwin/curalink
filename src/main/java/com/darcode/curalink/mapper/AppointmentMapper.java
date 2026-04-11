package com.darcode.curalink.mapper;

import com.darcode.curalink.dto.appointment.AppointmentResponse;
import com.darcode.curalink.dto.appointment.ScheduleAppointmentResponse;
import com.darcode.curalink.model.Appointment;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class AppointmentMapper {
    public ScheduleAppointmentResponse toScheduleAppointmentResponse(Appointment appointment) {
        return new ScheduleAppointmentResponse(
                appointment.getId(),
                appointment.getStatus(),
                appointment.getReason(),
                new ScheduleAppointmentResponse.DoctorInfo(
                        appointment.getDoctor().getId(),
                        appointment.getDoctor().getFirstName(),
                        appointment.getDoctor().getEmail(),
                        appointment.getDoctor().getDoctorProfile().getConsultationFee()
                ),
                new ScheduleAppointmentResponse.PatientInfo(
                        appointment.getPatient().getId(),
                        appointment.getPatient().getFirstName(),
                        appointment.getPatient().getEmail()
                )
        );
    }

    public AppointmentResponse toAppointmentResponse(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getSheduledAt(),
                appointment.getStatus(),
                appointment.getReason(),
                new ScheduleAppointmentResponse.DoctorInfo(
                        appointment.getDoctor().getId(),
                        appointment.getDoctor().getFirstName(),
                        appointment.getDoctor().getEmail(),
                        appointment.getDoctor().getDoctorProfile().getConsultationFee()
                ),
                new ScheduleAppointmentResponse.PatientInfo(
                        appointment.getPatient().getId(),
                        appointment.getPatient().getFirstName(),
                        appointment.getPatient().getEmail()
                ),
                new AppointmentResponse.TimeSlotInfo(
                        appointment.getTimeSlot().getStartTime(),
                        appointment.getTimeSlot().getEndTime(),
                        appointment.getTimeSlot().getIsAvailable(),
                        Duration.between(
                                appointment.getTimeSlot().getStartTime(),
                                appointment.getTimeSlot().getEndTime()
                        )
                )
        );
    }
}
