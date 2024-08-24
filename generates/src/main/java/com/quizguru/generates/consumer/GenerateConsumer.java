package com.quizguru.generates.consumer;

import com.quizguru.generates.dto.request.ChatRequest;
import com.quizguru.generates.dto.request.client.GenerateVocabByFileRequest;
import com.quizguru.generates.dto.request.client.GenerateVocabByListRequest;
import com.quizguru.generates.dto.request.client.GenerateVocabByTextRequest;
import com.quizguru.generates.dto.request.client.GenerateRequest;
import com.quizguru.generates.dto.request.text.TextRequest;
import com.quizguru.generates.dto.request.vocabulary.HandledFileVocabRequest;
import com.quizguru.generates.dto.request.vocabulary.ListVocabRequest;
import com.quizguru.generates.dto.request.vocabulary.TextVocabRequest;
import com.quizguru.generates.enums.QuizType;
import com.quizguru.generates.enums.Level;
import com.quizguru.generates.properties.GenerateProperties;
import com.quizguru.generates.properties.PromptProperties;
import com.quizguru.generates.service.GenerateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Slf4j
@Component
public class GenerateConsumer {
    private final GenerateService generateService;
    private final GenerateProperties generateProperties;
    private final PromptProperties promptProperties;

    @RabbitListener(queues = "#{amqpProperties.queues.generation}" )
    public void generateQuizByTextConsumer(GenerateRequest generateRequest) {

        TextRequest textRequest = TextRequest.builder()
                .quizId(generateRequest.quizId())
                .content(generateRequest.content())
                .htmlContent(generateRequest.htmlContext())
                .number(generateRequest.number())
                .level(Level.valueOf(generateRequest.level()))
                .language(generateRequest.language())
                .type(QuizType.valueOf(generateRequest.type()))
                .duration(generateRequest.duration())
                .build();
        ChatRequest chat = new ChatRequest(textRequest, promptProperties, generateProperties);
        generateService.generateQuiz(chat, generateRequest.userId());
    }

    @RabbitListener(queues = "#{amqpProperties.queues.listVocab}" )
    public void generateQuizVocabByListConsumer(GenerateVocabByListRequest generateVocabByListRequest) {

        List<String> lowerCaseWords = generateVocabByListRequest.names().stream()
                .map(String::toLowerCase)
                .map(String::trim)
                .toList();// Remove leading and trailing spaces

        ListVocabRequest listVocabRequest = ListVocabRequest.builder()
                .quizId(generateVocabByListRequest.quizId())
                .wordSetId(generateVocabByListRequest.wordSetId())
                .wordSetName(generateVocabByListRequest.wordSetName())
                .names(lowerCaseWords)
                .number(generateVocabByListRequest.number())
                .level(Level.valueOf(generateVocabByListRequest.level()))
                .language(generateVocabByListRequest.language())
                .type(QuizType.valueOf(generateVocabByListRequest.type()))
                .duration(generateVocabByListRequest.duration())
                .build();
        ChatRequest chat = new ChatRequest(listVocabRequest, promptProperties, generateProperties);
        generateService.generateQuiz(chat, generateVocabByListRequest.userId());
    }

    @RabbitListener(queues = "#{amqpProperties.queues.textVocab}" )
    public void generateQuizVocabByTextConsumer(GenerateVocabByTextRequest generateVocabByTextRequest) {

        TextVocabRequest textVocabRequest = TextVocabRequest.builder()
                .quizId(generateVocabByTextRequest.quizId())
                .wordSetId(generateVocabByTextRequest.wordSetId())
                .wordSetName(generateVocabByTextRequest.wordSetName())
                .content(generateVocabByTextRequest.content())
                .number(generateVocabByTextRequest.number())
                .level(Level.valueOf(generateVocabByTextRequest.level()))
                .language(generateVocabByTextRequest.language())
                .type(QuizType.valueOf(generateVocabByTextRequest.type()))
                .duration(generateVocabByTextRequest.duration())
                .build();
        ChatRequest chat = new ChatRequest(textVocabRequest, promptProperties, generateProperties);
        generateService.generateQuiz(chat, generateVocabByTextRequest.userId());
    }

    @RabbitListener(queues = "#{amqpProperties.queues.fileVocab}" )
    public void generateQuizVocabByFileConsumer(GenerateVocabByFileRequest generateVocabByFileRequest) {

        HandledFileVocabRequest handledFileVocabRequest = HandledFileVocabRequest.builder()
                .quizId(generateVocabByFileRequest.quizId())
                .wordSetId(generateVocabByFileRequest.wordSetId())
                .wordSetName(generateVocabByFileRequest.wordSetName())
                .number(generateVocabByFileRequest.number())
                .level(Level.valueOf(generateVocabByFileRequest.level()))
                .language(generateVocabByFileRequest.language())
                .type(QuizType.valueOf(generateVocabByFileRequest.type()))
                .duration(generateVocabByFileRequest.duration())
                .fileContent(generateVocabByFileRequest.fileContent())
                .build();
        ChatRequest chat = new ChatRequest(handledFileVocabRequest, promptProperties, generateProperties);
        generateService.generateQuiz(chat, generateVocabByFileRequest.userId());
    }

}
