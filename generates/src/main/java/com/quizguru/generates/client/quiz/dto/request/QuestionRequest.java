package com.quizguru.generates.client.quiz.dto.request;

import com.quizguru.generates.enums.QuestionType;

import java.util.List;

public record QuestionRequest(
        String query,
        List<String> choices,
        List<Integer> answers,
        QuestionType type,
        String explanation
) {
}
