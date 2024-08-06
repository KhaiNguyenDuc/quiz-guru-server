package com.quizguru.generates.consumer;

import com.quizguru.generates.dto.request.ChatRequest;
import com.quizguru.generates.dto.request.client.GenerateVocabByFileRequest;
import com.quizguru.generates.dto.request.client.GenerateVocabByTextRequest;
import com.quizguru.generates.dto.request.QuizGenerationResult;
import com.quizguru.generates.dto.request.client.GenerateRequest;
import com.quizguru.generates.dto.request.text.BaseTextRequest;
import com.quizguru.generates.dto.request.vocabulary.HandledFileVocabRequest;
import com.quizguru.generates.dto.request.vocabulary.TextVocabRequest;
import com.quizguru.generates.enums.ContextType;
import com.quizguru.generates.enums.Level;
import com.quizguru.generates.amqp.properties.GenerateProperties;
import com.quizguru.generates.amqp.properties.PromptProperties;
import com.quizguru.generates.service.GenerateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Slf4j
@Component
public class GenerateConsumer {
    private final GenerateService generateService;
    private final GenerateProperties generateProperties;
    private final PromptProperties promptProperties;

    @RabbitListener(queues = "#{amqpProperties.queues.generation}" )
    public void generateQuizByTextConsumer(GenerateRequest generateRequest) {

        BaseTextRequest baseTextRequest = BaseTextRequest.builder()
                .content(generateRequest.content())
                .htmlContent(generateRequest.htmlContext())
                .number(generateRequest.number())
                .level(Level.valueOf(generateRequest.level()))
                .language(generateRequest.language())
                .type(ContextType.valueOf(generateRequest.type()))
                .duration(generateRequest.duration())
                .build();
        ChatRequest chat = new ChatRequest(baseTextRequest, promptProperties, generateProperties);
        QuizGenerationResult result = generateService.generateQuiz(chat);
        log.info(result.toString());
    }

    @RabbitListener(queues = "#{amqpProperties.queues.textVocab}" )
    public void generateQuizVocabByTextConsumer(GenerateVocabByTextRequest generateVocabByTextRequest) {

        TextVocabRequest textVocabRequest = TextVocabRequest.builder()
                .names(generateVocabByTextRequest.names())
                .number(generateVocabByTextRequest.number())
                .level(Level.valueOf(generateVocabByTextRequest.level()))
                .language(generateVocabByTextRequest.language())
                .type(ContextType.valueOf(generateVocabByTextRequest.type()))
                .duration(generateVocabByTextRequest.duration())
                .build();
        ChatRequest chat = new ChatRequest(textVocabRequest, promptProperties, generateProperties);
        QuizGenerationResult result = generateService.generateQuiz(chat);
        log.info(result.toString());
    }

    @RabbitListener(queues = "#{amqpProperties.queues.fileVocab}" )
    public void generateQuizVocabByFileConsumer(GenerateVocabByFileRequest generateVocabByFileRequest) {

        HandledFileVocabRequest handledFileVocabRequest = HandledFileVocabRequest.builder()
                .number(generateVocabByFileRequest.number())
                .level(Level.valueOf(generateVocabByFileRequest.level()))
                .language(generateVocabByFileRequest.language())
                .type(ContextType.valueOf(generateVocabByFileRequest.type()))
                .duration(generateVocabByFileRequest.duration())
                .fileContent(generateVocabByFileRequest.fileContent())
                .build();
        ChatRequest chat = new ChatRequest(handledFileVocabRequest, promptProperties, generateProperties);
        QuizGenerationResult result = generateService.generateQuiz(chat);
        log.info(result.toString());
    }

}
