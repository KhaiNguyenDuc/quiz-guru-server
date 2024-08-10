package com.quizguru.quizzes.dto.request.text;

public record BaseRequest(
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
