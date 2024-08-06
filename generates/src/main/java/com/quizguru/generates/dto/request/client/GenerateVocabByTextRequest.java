package com.quizguru.generates.dto.request.client;


import java.util.List;

public record GenerateVocabByTextRequest(
        String quizId,
        List<String> names,
        String type,
        Integer number,
        String language,
        String level,
        Integer duration
) {}