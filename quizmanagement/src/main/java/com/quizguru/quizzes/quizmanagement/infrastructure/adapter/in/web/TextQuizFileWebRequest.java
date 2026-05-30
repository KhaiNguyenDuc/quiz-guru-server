package com.quizguru.quizzes.quizmanagement.infrastructure.adapter.in.web;

import org.springframework.web.multipart.MultipartFile;

public record TextQuizFileWebRequest(
        String type, Integer number, String language, String level, Integer duration,
        MultipartFile file
) {}
