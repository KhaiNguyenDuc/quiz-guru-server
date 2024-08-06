package com.quizguru.quizzes.dto.request;

public record HandledFileVocabRequest(
    String quizId,
    String type,
    Integer number,
    String language,
    String level,
    Integer duration,
    String fileContent
) {
}
