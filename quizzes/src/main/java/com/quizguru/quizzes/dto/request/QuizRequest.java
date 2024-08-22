package com.quizguru.quizzes.dto.request;

import java.util.List;

public record QuizRequest(List<QuestionRequest> questions) {
}
