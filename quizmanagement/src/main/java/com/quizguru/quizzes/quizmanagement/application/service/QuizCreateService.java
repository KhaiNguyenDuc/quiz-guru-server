package com.quizguru.quizzes.quizmanagement.application.service;

import com.quizguru.quizzes.quizmanagement.application.port.in.textquiz.create.CreateTextQuizCommand;
import com.quizguru.quizzes.quizmanagement.application.port.in.textquiz.create.CreateTextQuizResult;
import com.quizguru.quizzes.quizmanagement.application.port.in.textquiz.create.CreateTextQuizUseCase;
import com.quizguru.quizzes.quizmanagement.application.port.out.AIProviderPort;
import com.quizguru.quizzes.quizmanagement.application.port.out.GenerateQuizPrompt;
import com.quizguru.quizzes.quizmanagement.application.port.out.QuizPersistencePort;
import com.quizguru.quizzes.quizmanagement.domain.model.Quiz;
import com.quizguru.quizzes.quizmanagement.domain.model.enums.Level;
import com.quizguru.quizzes.quizmanagement.domain.model.enums.QuizType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuizCreateService implements CreateTextQuizUseCase {

    private final QuizPersistencePort quizPersistencePort;
    private final AIProviderPort aiProviderPort;

    @Override
    public CreateTextQuizResult create(CreateTextQuizCommand command) {

        Quiz quiz = Quiz.create()
                .userId(command.userId())
                .number(command.number())
                .duration(command.duration())
                .level(Level.valueOf(command.level()))
                .quizType(QuizType.valueOf(command.quizType()))
                .language(command.language())
                .givenText(command.content())
                .build();

        Quiz quizSaved = quizPersistencePort.save(quiz);

        GenerateQuizPrompt aiPrompt = GenerateQuizPrompt.fromDomain(quizSaved);

        aiProviderPort.generateQuiz(aiPrompt);
        return CreateTextQuizResult.fromDomain(quizSaved);
    }
}
