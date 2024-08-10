package com.quizguru.records.dto.request;

import java.util.List;

public record RecordItemRequest(String questionId, String explanation, List<String> selectedChoiceId) {
}
