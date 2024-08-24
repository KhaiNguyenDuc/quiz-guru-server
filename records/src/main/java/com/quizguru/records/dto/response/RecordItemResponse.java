package com.quizguru.records.dto.response;

import com.quizguru.records.dto.client.ChoiceResponse;
import com.quizguru.records.dto.client.QuestionResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record RecordItemResponse(QuestionResponse question, List<ChoiceResponse> selectedChoices) {
}
