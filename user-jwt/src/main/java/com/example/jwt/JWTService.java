package com.example.jwt;

import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Singleton;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Singleton
public class JWTService {
    public String generateUniversalToken() {
        Set<String> roles = new HashSet<>(Arrays.asList("ADMIN", "USER"));
        return Jwt.issuer("user-jwt")
                .subject("user-jwt")
                .groups(roles)
                .expiresAt(Instant.now().plus(Duration.ofHours(1)))
                .sign();
    }

    public String generateAdminToken() {
        Set<String> roles = new HashSet<>(List.of("ADMIN"));
        return Jwt.issuer("user-jwt")
                .subject("user-jwt")
                .groups(roles)
                .expiresAt(Instant.now().plus(Duration.ofHours(1)))
                .sign();
    }

    public String generateUserToken() {
        Set<String> roles = new HashSet<>(List.of("USER"));
        return Jwt.issuer("user-jwt")
                .subject("user-jwt")
                .groups(roles)
                .expiresAt(Instant.now().plus(Duration.ofHours(1)))
                .sign();
    }
}
