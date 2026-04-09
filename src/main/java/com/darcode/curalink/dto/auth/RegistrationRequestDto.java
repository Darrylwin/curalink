package com.darcode.curalink.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegistrationRequestDto(
        @NotBlank(message = "You should provide your first name")
        String firstName,

        @Email
        String email,

        @NotBlank(message = "You should provide a password")
        String password
) {
}
