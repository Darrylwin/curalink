package com.darcode.curalink.controller;

import com.darcode.curalink.dto.medicalRecord.MedicalRecordResponse;
import com.darcode.curalink.dto.shared.ApiResponse;
import com.darcode.curalink.dto.shared.PaginatedResponse;
import com.darcode.curalink.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/medical-records")
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<ApiResponse<PaginatedResponse<MedicalRecordResponse>>> getUserMedicalRecords(
            @PageableDefault Pageable pageable
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        Page<MedicalRecordResponse> medicalRecords = medicalRecordService.findUserRecords(userEmail, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(new PaginatedResponse<>(medicalRecords)));
    }
}
