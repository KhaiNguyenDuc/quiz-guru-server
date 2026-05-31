package com.quizguru.quizzes.quizmanagement.application.service;

import com.quizguru.quizzes.quizmanagement.application.port.in.textquiz.create.CreateTextQuizCommand;
import com.quizguru.quizzes.quizmanagement.application.port.in.textquiz.create.CreateTextQuizResult;
import com.quizguru.quizzes.quizmanagement.application.port.in.textquiz.create.CreateTextQuizUseCase;
import com.quizguru.quizzes.quizmanagement.application.port.out.GenerateTextQuizPrompt;
import com.quizguru.quizzes.quizmanagement.application.port.out.QuizEventPublisherPort;
import com.quizguru.quizzes.quizmanagement.application.port.out.QuizPersistencePort;
import com.quizguru.quizzes.quizmanagement.domain.model.Quiz;
import com.quizguru.quizzes.quizmanagement.domain.model.enums.Level;
import com.quizguru.quizzes.quizmanagement.domain.model.enums.QuizType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizCreateService implements CreateTextQuizUseCase {

    private final QuizPersistencePort quizPersistencePort;
    private final QuizEventPublisherPort quizEventPublisherPort;

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

        GenerateTextQuizPrompt prompt = GenerateTextQuizPrompt.fromDomain(quizSaved);

        quizEventPublisherPort.publishQuizGenerationRequest(prompt);
        return CreateTextQuizResult.fromDomain(quizSaved);
    }
}
