package com.darcode.curalink.controller;

import com.darcode.curalink.dto.appointment.ScheduleAppointmentRequest;
import com.darcode.curalink.dto.appointment.ScheduleAppointmentResponse;
import com.darcode.curalink.dto.shared.ApiResponse;
import com.darcode.curalink.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<ScheduleAppointmentResponse>> scheduleAnAppointment(ScheduleAppointmentRequest scheduleRequest) {
        ScheduleAppointmentResponse response = appointmentService.schedule(scheduleRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Appointment successfully scheduled"));
    }
}
