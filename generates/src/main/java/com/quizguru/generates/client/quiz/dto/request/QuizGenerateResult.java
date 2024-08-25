package com.quizguru.generates.client.quiz.dto.request;

import lombok.Builder;

@Builder
public record QuizGenerateResult(QuizRequest quizRequest, String quizId) {

}
