package com.darcode.curalink.controller;

import com.darcode.curalink.dto.doctors.DefineTimeSlotRequest;
import com.darcode.curalink.dto.doctors.DefineTimeSlotResponse;
import com.darcode.curalink.dto.doctors.DoctorAvailableSlotResponse;
import com.darcode.curalink.dto.doctors.DoctorResponse;
import com.darcode.curalink.dto.shared.ApiResponse;
import com.darcode.curalink.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctors")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<DoctorResponse>>> getAllDoctors(
            @RequestParam @Nullable String speciality,
            @RequestParam @Nullable Boolean disponibility,
            @PageableDefault(size = 10, page = 10) Pageable pageable
    ) {
        Page<DoctorResponse> doctors = doctorService.findAllBySpecialityAndDisponibility(
                speciality,
                disponibility,
                pageable
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(doctors));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorResponse>> getDoctorById(@PathVariable Integer id) {
        DoctorResponse doctor = doctorService.findById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(doctor));
    }

    @GetMapping("/{id}/available-slots")
    public ResponseEntity<ApiResponse<Page<DoctorAvailableSlotResponse>>> getDoctorAvailableSlots(
            @PathVariable Integer id, @PageableDefault Pageable pageable
    ) {
        Page<DoctorAvailableSlotResponse> slots = doctorService.getAvailableSlots(id, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(slots));
    }

    @PostMapping("/slots")
    public ResponseEntity<ApiResponse<DefineTimeSlotResponse>> createSlot(@Valid @RequestBody DefineTimeSlotRequest timeSlotRequest) {
        DefineTimeSlotResponse response = doctorService.defineTimeSlot(timeSlotRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Slot successfully defined"));
    }
}
