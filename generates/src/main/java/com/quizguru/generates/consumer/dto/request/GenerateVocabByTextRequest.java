package com.quizguru.generates.consumer.dto.request;

public record GenerateVocabByTextRequest(
        String userId,
        String quizId,
        String wordSetId,
        String wordSetName,
        String content,
        String type,
        Integer number,
        String language,
        String level,
        Integer duration
) {}