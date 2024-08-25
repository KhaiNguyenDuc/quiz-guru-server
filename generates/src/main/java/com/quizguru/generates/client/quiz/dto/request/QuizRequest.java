package com.quizguru.generates.client.quiz.dto.request;

import java.util.List;

public record QuizRequest(List<QuestionRequest> questions) {
}
