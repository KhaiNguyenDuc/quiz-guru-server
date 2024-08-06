package com.quizguru.generates.dto.request.client;

public record GenerateVocabByFileRequest(
        String quizId,
        String type,
        Integer number,
        String language,
        String level,
        Integer duration,
        String fileContent
) {
}
