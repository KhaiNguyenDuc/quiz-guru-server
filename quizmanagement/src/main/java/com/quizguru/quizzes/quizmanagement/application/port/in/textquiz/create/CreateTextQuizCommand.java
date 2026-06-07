package com.quizguru.quizzes.quizmanagement.application.port.in.textquiz.create;

import lombok.Builder;

@Builder
public record CreateTextQuizCommand(
        String userId,
        String quizType,
        Integer number,
        String language,
        String level,
        Integer duration,
        String content,
        String htmlContext
) {
    public CreateTextQuizCommand withId(String userId) {
        return new CreateTextQuizCommand(
                userId, quizType(),
                number(), language(), level(), duration(), content(), htmlContext());
    }
}