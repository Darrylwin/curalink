package com.darcode.curalink;

import com.darcode.curalink.dto.auth.LoginRequestDto;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.concurrent.atomic.AtomicReference;

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
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("DB_HOST", postgres::getHost);
        registry.add("DB_PORT", () -> postgres.getMappedPort(5432));
        registry.add("DB_NAME", () -> "curalink_test");
        registry.add("DB_USERNAME", () -> "test");
        registry.add("DB_PASSWORD", () -> "test");
        registry.add("MAIL_HOST", () -> "smtp.gmail.com");
        registry.add("MAIL_PORT", () -> "587");
        registry.add("MAIL_USERNAME", () -> "test@test.com");
        registry.add("MAIL_PASSWORD", () -> "test");
        registry.add("CORS_ALLOWED_ORIGINS", () -> "http://localhost:3000");
        registry.add("JWT_SECRET_KEY", () -> "dGVzdC1zZWNyZXQta2V5LXBvdXItbGVzLXRlc3RzLXVuaXRhaXJlcy0yMDI2");
    }

    protected WebTestClient webTestClient;

    @LocalServerPort
    protected int port;

    @BeforeEach
    void initWebTestClient() {
        this.webTestClient = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    protected String baseUrl() {
        return "/api";
    }

    protected String loginAndGetToken(String email, String password) {
        LoginRequestDto request = new LoginRequestDto(email, password);

        AtomicReference<String> token = new AtomicReference<>();

        webTestClient
                .post()
                .uri(baseUrl() + "/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.accessToken").value(token::set);

        return token.get();
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
