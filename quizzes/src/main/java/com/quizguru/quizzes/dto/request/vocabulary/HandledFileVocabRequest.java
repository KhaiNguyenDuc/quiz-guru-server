package com.quizguru.quizzes.dto.request.vocabulary;

public record HandledFileVocabRequest(
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
