package com.darcode.curalink.controller;

import com.darcode.curalink.dto.doctors.DefineTimeSlotRequest;
import com.darcode.curalink.dto.doctors.DefineTimeSlotResponse;
import com.darcode.curalink.dto.doctors.DoctorAvailableSlotResponse;
import com.darcode.curalink.dto.doctors.DoctorResponse;
import com.darcode.curalink.dto.shared.ApiResponse;
import com.darcode.curalink.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> getAllDoctors() {
        List<DoctorResponse> doctors = doctorService.findAll();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(doctors));
    }

    @GetMapping("/{id}") // TODO: add pagination and filters on speciality and disponibility
    public ResponseEntity<ApiResponse<DoctorResponse>> getDoctorById(@PathVariable Integer id) {
        DoctorResponse doctor = doctorService.findById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(doctor));
    }

    @GetMapping("/{id}/available-slots") // TODO: add pagination
    public ResponseEntity<ApiResponse<List<DoctorAvailableSlotResponse>>> getDoctorAvailableSlots(@PathVariable Integer id) {
        List<DoctorAvailableSlotResponse> slots = doctorService.getAvailableSlots(id);

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
