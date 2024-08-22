package com.quizguru.generates.dto.request.client;

public record GenerateRequest(
        String userId,
        String quizId,
        String wordSetId,
        String content,
        String htmlContext,
        String type,
        Integer number,
        String language,
        String level,
        Integer duration
) {
}
