package com.quizguru.quizzes.dto.request;


import lombok.Builder;

import java.util.List;

@Builder
public record RecordRequest(String quizId, List<RecordItemRequest> recordItems, Integer timeLeft) {
}