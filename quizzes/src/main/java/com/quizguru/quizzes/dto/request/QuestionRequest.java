package com.quizguru.quizzes.dto.request;

import com.quizguru.quizzes.model.enums.QuestionType;

import java.util.List;

public record QuestionRequest(
        String query,
        List<String> choices,
        List<Integer> answers,
        QuestionType type,
        String explanation
) {
}
