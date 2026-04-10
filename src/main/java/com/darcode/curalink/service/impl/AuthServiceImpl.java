package com.darcode.curalink.service.impl;

import com.darcode.curalink.dto.auth.*;
import com.darcode.curalink.enums.Role;
import com.darcode.curalink.exception.ConflictException;
import com.darcode.curalink.model.User;
import com.darcode.curalink.repository.UserRepository;
import com.darcode.curalink.service.JwtService;
import com.darcode.curalink.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private final UserDetailsService userDetailsService;

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

    @Override
    public RefreshTokenResponseDto refresh(RefreshTokenRequestDto refreshTokenRequestDto) {
        try {
            // extract refresh token
            String email = jwtService.extractEmail(refreshTokenRequestDto.refreshToken());

            // load user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // validate token
            if(!jwtService.isRefreshTokenValid(refreshTokenRequestDto.refreshToken(), userDetails)) {
                log.warn("Invalid refresh token for user {}", email);
                throw new BadCredentialsException("Invalid or expired refresh token");
            }

            // generate a new access token
            String newAccessToken = jwtService.generateAccessToken(userDetails);
            // generate a new refresh token for rotation
            String newRefreshToken = jwtService.generateRefreshToken(userDetails);

            log.info("Token refreshed for user {}", email);
            return new RefreshTokenResponseDto(newAccessToken, newRefreshToken);
        } catch (Exception exception) {
            log.error("Error refreshing token");
            throw new BadCredentialsException("Invalid refresh token");
        }
    }
}
