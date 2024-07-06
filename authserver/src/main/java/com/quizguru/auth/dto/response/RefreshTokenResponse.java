package com.quizguru.auth.dto.response;

import lombok.Builder;

import java.time.Instant;

@Builder
public record RefreshTokenResponse(String token, Instant expiredDate) {
}
