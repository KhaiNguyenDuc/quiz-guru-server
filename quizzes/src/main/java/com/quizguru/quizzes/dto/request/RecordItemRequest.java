package com.quizguru.quizzes.dto.request;

import lombok.Builder;

import java.util.List;

@Builder
public record RecordItemRequest(String questionId, String explanation, List<String> selectedChoiceIds) {
}
