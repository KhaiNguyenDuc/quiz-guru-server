package com.quizguru.generates.consumer.dto.request;

public record GenerateVocabByFileRequest(
        String userId,
        String quizId,
        String wordSetId,
        String wordSetName,
        String type,
        Integer number,
        String language,
        String level,
        Integer duration,
        String fileContent
) {
}
