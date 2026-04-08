package com.darcode.curalink.service;

import com.darcode.curalink.dto.RegistrationRequestDto;
import com.darcode.curalink.dto.RegistrationResponseDto;

public interface UserService {
    RegistrationResponseDto register(RegistrationRequestDto registrationRequestDto);
}
