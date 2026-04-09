package com.darcode.curalink.dto;

public record LoginResponseDto(
        String firstName,
        String accessToken,
        String refreshToken
) {
}
