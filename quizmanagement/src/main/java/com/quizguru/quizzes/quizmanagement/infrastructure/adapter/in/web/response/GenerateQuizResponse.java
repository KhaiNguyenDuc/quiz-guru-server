package com.quizguru.quizzes.quizmanagement.infrastructure.adapter.in.web.response;

import com.quizguru.quizzes.quizmanagement.domain.model.enums.Level;
import com.quizguru.quizzes.quizmanagement.domain.model.enums.QuizType;
import lombok.Builder;

@Builder
public record GenerateQuizResponse(
        String quizId,
        Integer number,
        Level level,
        Integer duration,
        QuizType type,
        String language,
        String givenText
) {
}
