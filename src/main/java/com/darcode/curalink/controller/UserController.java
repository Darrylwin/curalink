package com.darcode.curalink.controller;

import com.darcode.curalink.dto.*;
import com.darcode.curalink.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegistrationResponseDto>> register(@Valid @RequestBody RegistrationRequestDto registrationRequestDto) {
        RegistrationResponseDto response = userService.register(registrationRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, response.message()));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(
            @Valid @RequestBody LoginRequestDto loginRequestDto,
            HttpServletResponse response
    ) {
        LoginResponseDto loginResponseDto = userService.login(loginRequestDto);
        Cookie cookie = new Cookie("refreshToken", loginResponseDto.refreshToken());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(loginResponseDto, "Login successfull"));
    }
}
