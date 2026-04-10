package com.darcode.curalink.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface JwtService {

    // Extract user firstName
    String extractEmail(String token);

    // Extract a single claim
    <T> T extractClaim(String token, Function<Claims, T> claimResolver);

    // Generate the token
    String generateAccessToken(UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);

    // Validate a token
    boolean isTokenValid(String token, UserDetails userDetails);
}
