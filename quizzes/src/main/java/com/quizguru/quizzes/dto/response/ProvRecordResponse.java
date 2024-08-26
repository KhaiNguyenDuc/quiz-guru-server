package com.quizguru.quizzes.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ProvRecordResponse(List<RecordItemResponse> recordItemResponses, String givenText, Integer duration) {
}