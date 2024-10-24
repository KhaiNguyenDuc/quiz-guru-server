package com.khai.quizguru.customers.dto.response;

import lombok.Builder;

import java.util.Set;

@Builder
public record TokenValidationResponse(String userId, Set<String> userAuthorities) {
}
