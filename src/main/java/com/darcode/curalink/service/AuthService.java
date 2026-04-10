package com.darcode.curalink.service;

import com.darcode.curalink.dto.auth.*;

public interface AuthService {
    RegistrationResponseDto register(RegistrationRequestDto registrationRequestDto);

    LoginResponseDto login(LoginRequestDto loginRequestDto);

    RefreshTokenResponseDto refresh(RefreshTokenRequestDto refreshTokenRequestDto);

}
