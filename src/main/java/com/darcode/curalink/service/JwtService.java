package com.darcode.curalink.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface JwtService {

    // Extract user firstName
    String extractFirstName(String token);

    // Extract a single claim
    <T> T extractClaim(String token, Function<Claims, T> claimResolver);

    // Generate the token
    String generateToken(UserDetails userDetails);

    // Validate a token
    boolean isTokenValid(String token, UserDetails userDetails);
}
