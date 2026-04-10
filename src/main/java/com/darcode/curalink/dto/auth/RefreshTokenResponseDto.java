package com.darcode.curalink.dto.auth;

public record RefreshTokenResponseDto (
        String accessToken,
        String refreshToken
) {
}
