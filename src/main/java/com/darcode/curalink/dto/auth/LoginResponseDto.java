package com.darcode.curalink.dto.auth;

public record LoginResponseDto(
        String firstName,
        String accessToken,
        String refreshToken
) {
}
