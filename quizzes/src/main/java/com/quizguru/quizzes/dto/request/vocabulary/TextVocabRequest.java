package com.quizguru.quizzes.dto.request.vocabulary;

import java.util.List;

public record TextVocabRequest(
        String quizId,
        List<String> names,
        String type,
        Integer number,
        String language,
        String level,
        Integer duration
) {}