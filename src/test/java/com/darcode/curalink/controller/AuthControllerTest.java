package com.darcode.curalink.controller;

import com.darcode.curalink.CuralinkApplicationTests;
import com.darcode.curalink.dto.auth.LoginRequestDto;
import com.darcode.curalink.dto.auth.RegistrationRequestDto;
import com.darcode.curalink.dto.shared.ErrorResponse;
import com.darcode.curalink.enums.Role;
import com.darcode.curalink.model.User;
import com.darcode.curalink.repository.UserRepository;
import com.github.dockerjava.api.model.AuthResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class AuthControllerTest extends CuralinkApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void should_register_patient_successfully() {
        var request = new RegistrationRequestDto(
                "Just Motion",
                "just@motion.com",
                "Kirin"
        );

        ResponseEntity<AuthResponse> response = restTemplate.postForEntity(
                baseUrl() + "/auth/register",
                request,
                AuthResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getIdentityToken()).isNotBlank();
        assertThat(userRepository.findByEmail("just@motion.com")).isPresent();
    }

    @Test
    void should_return_409_when_email_already_exists() {
        User existing = new User();
        existing.setEmail("just@motion.com");
        existing.setPassword(passwordEncoder.encode("Kirin"));
        existing.setRole(Role.PATIENT);
        userRepository.save(existing);

        var request = new RegistrationRequestDto(
                "Just Motion",
                "just@motion.com",
                "Kirin"
        );

        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(
                baseUrl() + "/auth/register",
                request,
                ErrorResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void should_login_successfully() {
        User user = new User();
        user.setEmail("just@motion.com");
        user.setPassword(passwordEncoder.encode("Kirin"));
        user.setRole(Role.PATIENT);
        userRepository.save(user);

        var request = new LoginRequestDto("just@motion.com", "Kirin");

        ResponseEntity<AuthResponse> response = restTemplate.postForEntity(
                baseUrl() + "/auth/login",
                request,
                AuthResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getIdentityToken()).isNotBlank();
    }

    @Test
    void should_return_401_when_credentials_are_wrong() {
        var request = new LoginRequestDto("nobody@gmail.com", "wrongpassword");

        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(
                baseUrl() + "/auth/login",
                request,
                ErrorResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}