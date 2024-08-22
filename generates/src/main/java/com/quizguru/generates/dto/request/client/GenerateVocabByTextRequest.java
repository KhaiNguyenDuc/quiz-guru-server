package com.quizguru.generates.dto.request.client;

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