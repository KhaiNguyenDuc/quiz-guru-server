package com.khai.quizguru.customers.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class KeycloakRoleConverterTest {

    private Jwt source;
    private KeycloakRoleConverter converter;

    private Jwt jwtWithClaims(Map<String, Object> claims) {
        return new Jwt(
                "token-value",
                Instant.now(),
                Instant.now().plusSeconds(120),
                Map.of("alg", "none"),
                claims
        );
    }

    @BeforeEach
    void setup(){
        converter = new KeycloakRoleConverter();
    }


    @Test
    void convert_ShouldReturnEmpty_WhenRealmAccessClaimMissing(){

        // Given
        Jwt jwt = jwtWithClaims(Map.of("userId", "TestID"));

        // When
        Collection<GrantedAuthority> result = converter.convert(jwt);

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    void convert_ShouldReturnEmpty_WhenRealmAccessIsEmpty() {

        // Given
        Jwt jwt = jwtWithClaims(Map.of("realm_access", Map.of()));

        // When
        Collection<GrantedAuthority> result = converter.convert(jwt);

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    void convert_ShouldReturnPrefixedAuthorities_WhenRolesPresent() {

        // Given
        Map<String, Object> realmAccess = Map.of("roles", List.of("admin", "user"));
        Jwt jwt = jwtWithClaims(Map.of("realm_access", realmAccess));

        // When
        Collection<GrantedAuthority> result = converter.convert(jwt);

        // Then
        assertEquals(2, result.size());
        Set<String> authorities = new HashSet<>();
        result.forEach(a -> authorities.add(a.getAuthority()));
        assertTrue(authorities.contains("ROLE_admin"));
        assertTrue(authorities.contains("ROLE_user"));
    }
}