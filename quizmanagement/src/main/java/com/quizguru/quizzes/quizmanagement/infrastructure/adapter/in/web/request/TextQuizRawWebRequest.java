package com.quizguru.quizzes.quizmanagement.infrastructure.adapter.in.web.request;

public record TextQuizRawWebRequest(
        String type, Integer number, String language, String level, Integer duration,
        String content,
        String htmlContext
) {}