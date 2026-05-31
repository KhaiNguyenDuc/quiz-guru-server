package com.quizguru.quizzes.quizmanagement.application.service;

import com.quizguru.quizzes.quizmanagement.application.port.in.consumer.GenerationTextQuizCommand;
import com.quizguru.quizzes.quizmanagement.application.port.in.consumer.GenerationTextQuizUseCase;
import com.quizguru.quizzes.quizmanagement.application.port.out.AIProviderPort;
import com.quizguru.quizzes.quizmanagement.application.port.out.GenerateTextQuizPrompt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizGenerationProcessorService implements GenerationTextQuizUseCase {

    private final AIProviderPort aiProviderPort;

    @Override
    public void process(GenerationTextQuizCommand command) {

        GenerateTextQuizPrompt generateTextQuizPrompt = GenerateTextQuizPrompt.builder()
                .userId(command.userId())
                .quizId(command.quizId())
                .givenText(command.givenText())
                .quizType(command.quizType())
                .number(command.number())
                .language(command.language())
                .level(command.level())
                .duration(command.duration())
                .build();
        aiProviderPort.generateQuiz(generateTextQuizPrompt);
    }
}
