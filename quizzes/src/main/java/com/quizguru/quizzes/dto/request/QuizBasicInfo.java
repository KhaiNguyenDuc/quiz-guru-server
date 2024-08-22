package com.quizguru.quizzes.dto.request;


import com.quizguru.quizzes.model.enums.Level;
import com.quizguru.quizzes.model.enums.QuizType;
import lombok.Builder;

@Builder
public record QuizBasicInfo(
        String id,
        String givenText,
        Level level,
        String language,
        Integer number,
        QuizType type,
        Integer duration,
        String userId,
        QuestionRequest QuestionRequest
) {
}