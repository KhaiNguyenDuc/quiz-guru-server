package com.quizguru.quizzes.dto.request.text;

import org.springframework.web.multipart.MultipartFile;

public record HandledFileRequest (
        String userId,
        String quizId,
        String type,
        Integer number,
        String language,
        String level,
        Integer duration,
        String fileContent
){
}
