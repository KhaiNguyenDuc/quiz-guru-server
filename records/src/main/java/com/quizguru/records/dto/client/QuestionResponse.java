package com.quizguru.records.dto.client;

import lombok.Builder;

import java.util.List;

@Builder
public record QuestionResponse(String id, String query, List<ChoiceResponse> choices, String explanation, String type) {

}
