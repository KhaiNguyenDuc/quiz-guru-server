package com.quizguru.quizzes.dto.request.text;

import org.springframework.web.multipart.MultipartFile;

public record RawFileRequest (
        String quizId,
        String type,
        Integer number,
        String language,
        String level,
        Integer duration,
        MultipartFile file
)
{
}