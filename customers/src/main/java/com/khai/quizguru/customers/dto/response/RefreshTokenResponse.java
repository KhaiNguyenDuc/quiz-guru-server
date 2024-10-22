package com.khai.quizguru.customers.dto.response;

import lombok.Builder;

import java.time.Instant;

@Builder
public record RefreshTokenResponse(String token, Instant expiredDate) {
}
