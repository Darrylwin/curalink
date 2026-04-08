package com.darcode.curalink.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @Email
        String email,

        @NotBlank(message = "You should provide a password")
        String password
) {
}
