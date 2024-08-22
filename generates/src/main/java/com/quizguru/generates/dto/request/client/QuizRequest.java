package com.quizguru.generates.dto.request.client;

import java.util.List;

public record QuizRequest(List<QuestionRequest> questions) {
}
