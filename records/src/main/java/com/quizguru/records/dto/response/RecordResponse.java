package com.quizguru.records.dto.response;

import lombok.Builder;

@Builder
public record RecordResponse(String id, Integer score, Integer duration, Integer timeLeft, String userId, String quizId) {
}
