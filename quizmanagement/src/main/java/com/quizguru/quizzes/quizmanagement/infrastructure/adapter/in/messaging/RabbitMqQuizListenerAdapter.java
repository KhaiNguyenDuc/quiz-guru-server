package com.quizguru.quizzes.quizmanagement.infrastructure.adapter.in.messaging;

import com.quizguru.quizzes.quizmanagement.application.port.in.consumer.GenerationTextQuizCommand;
import com.quizguru.quizzes.quizmanagement.application.port.in.consumer.GenerationTextQuizUseCase;
import com.quizguru.quizzes.quizmanagement.application.port.out.GenerateTextQuizPrompt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class RabbitMqQuizListenerAdapter {

    private final GenerationTextQuizUseCase generationTextQuizUseCase;

    @RabbitListener(queues = "#{amqpProperties.queues.generation}" )
    public void generateQuizByTextConsumer(GenerateTextQuizPrompt generateTextQuizPrompt){

        GenerationTextQuizCommand generationTextQuizCommand = GenerationTextQuizCommand.fromPrompt(generateTextQuizPrompt);

        generationTextQuizUseCase.process(generationTextQuizCommand);
    }
}
