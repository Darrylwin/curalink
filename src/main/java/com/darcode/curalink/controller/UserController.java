package com.darcode.curalink.controller;

import com.darcode.curalink.dto.ApiResponse;
import com.darcode.curalink.dto.RegistrationRequestDto;
import com.darcode.curalink.dto.RegistrationResponseDto;
import com.darcode.curalink.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegistrationResponseDto>> register(@RequestBody RegistrationRequestDto registrationRequestDto) {
        RegistrationResponseDto response = userService.register(registrationRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, response.message()));
    }
}
