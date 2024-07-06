package com.quizguru.gateway.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record TokenValidationResponse(String userId, Set<String> userAuthorities) {
}
