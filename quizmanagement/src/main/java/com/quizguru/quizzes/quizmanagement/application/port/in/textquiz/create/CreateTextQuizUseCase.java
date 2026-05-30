package com.quizguru.quizzes.quizmanagement.application.port.in.textquiz.create;

public interface CreateTextQuizUseCase {
    CreateTextQuizResult create(CreateTextQuizCommand command);
}
