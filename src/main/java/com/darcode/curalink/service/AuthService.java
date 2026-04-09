package com.darcode.curalink.service;

import com.darcode.curalink.dto.auth.LoginRequestDto;
import com.darcode.curalink.dto.auth.LoginResponseDto;
import com.darcode.curalink.dto.auth.RegistrationRequestDto;
import com.darcode.curalink.dto.auth.RegistrationResponseDto;

public interface AuthService {
    RegistrationResponseDto register(RegistrationRequestDto registrationRequestDto);

    LoginResponseDto login(LoginRequestDto loginRequestDto);

}
