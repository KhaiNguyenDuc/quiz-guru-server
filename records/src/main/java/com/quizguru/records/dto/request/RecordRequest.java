package com.quizguru.records.dto.request;

import lombok.Builder;

import java.util.List;

@Builder
public record RecordRequest(String quizId, List<RecordItemRequest> recordItems, Integer timeLeft) {
}
