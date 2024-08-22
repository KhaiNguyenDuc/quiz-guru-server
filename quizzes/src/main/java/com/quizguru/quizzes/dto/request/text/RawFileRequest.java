package com.quizguru.quizzes.dto.request.text;

import org.springframework.web.multipart.MultipartFile;

public record RawFileRequest (
        String userId,
        String quizId,
        String type,
        Integer number,
        String language,
        String level,
        Integer duration,
        MultipartFile file
)
{
    public RawFileRequest withId(String userId, String quizId) {
        return new RawFileRequest(userId, quizId, type(), number(), language(), level(), duration(), file());
    }
}