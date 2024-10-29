package com.quizguru.quizzes.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record SubmitRecordResponse(String recordId, List<String> correctIndex) {
}
