package com.quizguru.quizzes.quizmanagement.application.port.in.textquiz.create;

import com.quizguru.quizzes.quizmanagement.domain.model.Quiz;
import com.quizguru.quizzes.quizmanagement.domain.model.enums.Level;
import com.quizguru.quizzes.quizmanagement.domain.model.enums.QuizType;

public record CreateTextQuizResult(
        String quizId,
        Integer number,
        Level level,
        Integer duration,
        QuizType type,
        String language,
        String givenText)
{
    public static CreateTextQuizResult fromDomain(Quiz quiz){
        return new CreateTextQuizResult(
                quiz.getQuizId(),
                quiz.getNumber(),
                quiz.getLevel(),
                quiz.getDuration(),
                quiz.getQuizType(),
                quiz.getLanguage(),
                quiz.getGivenText()
        );
    }
}