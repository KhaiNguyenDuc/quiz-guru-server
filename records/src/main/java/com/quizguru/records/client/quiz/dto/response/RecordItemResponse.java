package com.quizguru.records.client.quiz.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record RecordItemResponse(QuestionResponse question, List<ChoiceResponse> selectedChoices) {
}
