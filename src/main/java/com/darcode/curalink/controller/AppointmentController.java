package com.darcode.curalink.controller;

import com.darcode.curalink.dto.appointment.AppointmentResponse;
import com.darcode.curalink.dto.appointment.ScheduleAppointmentRequest;
import com.darcode.curalink.dto.appointment.ScheduleAppointmentResponse;
import com.darcode.curalink.dto.shared.ApiResponse;
import com.darcode.curalink.dto.shared.PaginatedResponse;
import com.darcode.curalink.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<PaginatedResponse<AppointmentResponse>>> getUserAppointments(
            @PageableDefault Pageable pageable
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        Page<AppointmentResponse> appointments =
                appointmentService.findUserAppointments(userEmail, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(new PaginatedResponse<>(appointments)));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<String>> cancelAppointment(@PathVariable Integer id) {
        appointmentService.cancelAppointment(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("Appointment successfully canceled"));
    }
}
