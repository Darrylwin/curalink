package com.darcode.curalink.controller;

import com.darcode.curalink.CuralinkApplicationTests;
import com.darcode.curalink.dto.auth.LoginRequestDto;
import com.darcode.curalink.dto.auth.RegistrationRequestDto;
import com.darcode.curalink.enums.Role;
import com.darcode.curalink.model.User;
import com.darcode.curalink.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

        webTestClient
                .post()
                .uri(baseUrl() + "/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.token").isNotEmpty();

        assertThat(userRepository.findByEmail("just@motion.com")).isPresent();
    }

    @Test
    void should_return_409_when_email_already_exists() {
        User existing = new User();
        existing.setEmail("just@motion.com");
        existing.setPassword(passwordEncoder.encode("Kirin"));
        existing.setRole(Role.PATIENT);
        userRepository.save(existing);

        RegistrationRequestDto request = new RegistrationRequestDto(
                "Just Motion",
                "just@motion.com",
                "Kirin"
        );

        webTestClient
                .post()
                .uri(baseUrl() + "/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void should_login_successfully() {
        User user = new User();
        user.setEmail("just@motion.com");
        user.setPassword(passwordEncoder.encode("Kirin"));
        user.setRole(Role.PATIENT);
        userRepository.save(user);

        LoginRequestDto request = new LoginRequestDto("just@motion.com", "Kirin");

        webTestClient
                .post()
                .uri(baseUrl() + "/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.token").isNotEmpty();
    }

    @Test
    void should_return_401_when_credentials_are_wrong() {
        LoginRequestDto request = new LoginRequestDto("nobody@gmail.com", "wrongpassword");

        webTestClient
                .post()
                .uri(baseUrl() + "/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void should_return_400_when_email_is_invalid() {
        RegistrationRequestDto request = new RegistrationRequestDto(
                "Just Motion",
                "just@motion.com",
                "Kirin"
        );

        webTestClient
                .post()
                .uri(baseUrl() + "/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest();
    }
}