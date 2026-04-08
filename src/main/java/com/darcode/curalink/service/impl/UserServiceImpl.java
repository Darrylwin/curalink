package com.darcode.curalink.service.impl;

import com.darcode.curalink.dto.RegistrationRequestDto;
import com.darcode.curalink.dto.RegistrationResponseDto;
import com.darcode.curalink.model.User;
import com.darcode.curalink.repository.UserRepository;
import com.darcode.curalink.service.JwtService;
import com.darcode.curalink.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public RegistrationResponseDto register(RegistrationRequestDto registrationRequestDto) {
        User userExist = userRepository.findByEmail(registrationRequestDto.email()).orElse(null);

        if (userExist != null) {
            return new RegistrationResponseDto("User already exists");
        }

        User user = new User();
        user.setFirstName(registrationRequestDto.firstName());
        user.setEmail(registrationRequestDto.email());
        user.setPassword(passwordEncoder.encode(registrationRequestDto.password()));

        userRepository.save(user);

        return new RegistrationResponseDto("Registration Successfull");
    }
}
