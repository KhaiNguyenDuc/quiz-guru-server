package com.quizguru.generates.dto.request;

import com.quizguru.generates.dto.request.client.QuizRequest;
import lombok.Builder;

@Builder
public record QuizGenerateResult(QuizRequest quizRequest, String quizId) {

}
