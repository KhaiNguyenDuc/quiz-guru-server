package com.quizguru.records.dto.request;

import lombok.Builder;

import java.util.List;

@Builder
public record RecordItemRequest(String questionId, String explanation, List<String> selectedChoiceIds) {
}
