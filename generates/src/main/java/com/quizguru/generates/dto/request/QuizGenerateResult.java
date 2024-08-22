package com.quizguru.generates.dto.request;

import lombok.Builder;

@Builder
public record QuizGenerationResult(String chatResponse, QuizBasicInfo quizBasicInfo) {

}
