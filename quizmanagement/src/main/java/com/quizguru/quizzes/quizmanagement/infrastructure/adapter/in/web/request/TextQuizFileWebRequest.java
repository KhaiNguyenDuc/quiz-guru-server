package com.quizguru.quizzes.quizmanagement.infrastructure.adapter.in.web.request;

import org.springframework.web.multipart.MultipartFile;

public record TextQuizFileWebRequest(
        String type, Integer number, String language, String level, Integer duration,
        MultipartFile file
) {}
