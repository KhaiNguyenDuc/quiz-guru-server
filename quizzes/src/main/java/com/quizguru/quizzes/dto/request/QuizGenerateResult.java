package com.quizguru.quizzes.dto.request;

import lombok.Builder;

@Builder
public record QuizGenerateResult(QuizRequest quizRequest, String quizId) {

}
