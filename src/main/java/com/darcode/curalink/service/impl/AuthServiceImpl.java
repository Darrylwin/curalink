package com.darcode.curalink.service.impl;

import com.darcode.curalink.dto.LoginRequestDto;
import com.darcode.curalink.dto.LoginResponseDto;
import com.darcode.curalink.dto.RegistrationRequestDto;
import com.darcode.curalink.dto.RegistrationResponseDto;
import com.darcode.curalink.enums.Role;
import com.darcode.curalink.exception.ConflictException;
import com.darcode.curalink.model.User;
import com.darcode.curalink.repository.UserRepository;
import com.darcode.curalink.service.JwtService;
import com.darcode.curalink.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public RegistrationResponseDto register(RegistrationRequestDto registrationRequestDto) {
        User userExist = userRepository.findByEmail(registrationRequestDto.email()).orElse(null);

        if (userExist != null) {
            log.info("User {} already exists", registrationRequestDto.email());
            throw new ConflictException("User already exists");
        }

        User user = new User();
        user.setFirstName(registrationRequestDto.firstName());
        user.setEmail(registrationRequestDto.email());
        user.setPassword(passwordEncoder.encode(registrationRequestDto.password()));
        user.setRole(Role.PATIENT);

        userRepository.save(user);

        log.info("User {} created", registrationRequestDto.email());
        return new RegistrationResponseDto("Registration Successfull");
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequestDto.email(), loginRequestDto.password()
        ));

        User user = userRepository.findByEmail(loginRequestDto.email()).orElseThrow();
        UserDetails userDetails = new UserDetailsImpl(user);

        var accessToken = jwtService.generateAccessToken(userDetails);
        var refrshToken = jwtService.generateRefreshToken(userDetails);

        log.info("Token generated");
        log.info(("Login successfull"));
        return new LoginResponseDto(user.getFirstName(), accessToken, refrshToken);
    }
}
