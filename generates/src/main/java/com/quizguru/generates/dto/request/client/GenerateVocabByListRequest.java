package com.quizguru.generates.dto.request.client;

import java.util.List;

public record GenerateVocabByListRequest(
        String userId,
        String quizId,
        String wordSetId,
        String wordSetName,
        List<String> names,
        String type,
        Integer number,
        String language,
        String level,
        Integer duration
) {}