package com.quizguru.generates.consumer.dto.request;

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
