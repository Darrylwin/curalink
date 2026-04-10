package com.darcode.curalink.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface JwtService {

    // Extract user email
    String extractEmail(String token);

    // Extract a single claim
    <T> T extractClaim(String token, Function<Claims, T> claimResolver);

    // Generate access token
    String generateAccessToken(UserDetails userDetails);

    //    Generate refresh token
    String generateRefreshToken(UserDetails userDetails);

    // Validate a token
    boolean isTokenValid(String token, UserDetails userDetails);

    // extract if the token is an access or refresh
    String extractTokenType(String token);

    // validate a refresh token
    boolean isRefreshTokenValid(String token, UserDetails userDetails);
}
