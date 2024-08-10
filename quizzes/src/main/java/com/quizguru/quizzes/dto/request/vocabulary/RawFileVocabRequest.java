package com.quizguru.quizzes.dto.request.vocabulary;

import org.springframework.web.multipart.MultipartFile;

public record RawFileVocabRequest(
        String quizId,
        String type,
        Integer number,
        String language,
        String level,
        Integer duration,
        MultipartFile file
) {
}