package com.quizguru.quizzes.dto.request;

public record GenerateRequest(
        String quizId,
        String content,
        String htmlContext,
        String type,
        Integer number,
        String language,
        String level,
        Integer duration
) {
}
