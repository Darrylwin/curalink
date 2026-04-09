package com.darcode.curalink.service;

import com.darcode.curalink.dto.LoginRequestDto;
import com.darcode.curalink.dto.LoginResponseDto;
import com.darcode.curalink.dto.RegistrationRequestDto;
import com.darcode.curalink.dto.RegistrationResponseDto;

public interface AuthService {
    RegistrationResponseDto register(RegistrationRequestDto registrationRequestDto);

    LoginResponseDto login(LoginRequestDto loginRequestDto);

}
