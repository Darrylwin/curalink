package com.darcode.curalink;

import com.darcode.curalink.dto.auth.LoginRequestDto;
import com.darcode.curalink.dto.auth.LoginResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
public abstract class CuralinkApplicationTests {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("curalink_test")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword); // manquant
        registry.add("DB_HOST", postgres::getHost);
        registry.add("DB_PORT", () -> postgres.getMappedPort(5432));
        registry.add("DB_NAME", () -> "curalink_test");
        registry.add("DB_USERNAME", () -> "test");
        registry.add("DB_PASSWORD", () -> "test");
    }

    @Autowired
    protected WebTestClient webTestClient;

    @LocalServerPort
    protected int port;

    protected String baseUrl() {
        return "http://localhost:" + port + "/api";
    }

    protected String loginAndGetToken(String email, String password) {
        LoginRequestDto request = new LoginRequestDto(email, password);

        return Objects.requireNonNull(webTestClient
                        .post()
                        .uri(baseUrl() + "/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(request)
                        .exchange()
                        .expectStatus().isOk()
                        .expectBody(LoginResponseDto.class)
                        .returnResult()
                        .getResponseBody())
                .accessToken();
    }

    protected WebTestClient.RequestHeadersSpec<?> authenticatedGet(String url, String token) {
        return webTestClient
                .get()
                .uri(url)
                .header("Authorization", "Bearer " + token);
    }

    protected <T> WebTestClient.RequestHeadersSpec<?> authenticatedPost(String url, String token, T body) {
        return webTestClient
                .post()
                .uri(url)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body);
    }
}
