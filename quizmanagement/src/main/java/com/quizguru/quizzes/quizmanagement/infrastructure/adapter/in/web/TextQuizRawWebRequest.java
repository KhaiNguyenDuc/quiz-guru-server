package com.quizguru.quizzes.quizmanagement.infrastructure.adapter.in.web;

public record TextQuizRawWebRequest(
        String type, Integer number, String language, String level, Integer duration,
        String content,
        String htmlContext
) {}