package com.quizguru.generates.service.impl;

import com.quizguru.generates.properties.GenerateProperties;
import com.quizguru.generates.dto.request.ChatRequest;
import com.quizguru.generates.dto.response.ChatResponse;
import com.quizguru.generates.dto.request.QuizGenerationResult;
import com.quizguru.generates.service.GenerateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenerateServiceImpl implements GenerateService {

    private final RestTemplate restTemplate;

    @Override
    public QuizGenerationResult generateQuiz(ChatRequest chat) {

        GenerateProperties generateConfiguration = chat.getGenerateConfiguration();
        ChatResponse chatResponse = restTemplate.postForObject(generateConfiguration.getApiURL(), chat, ChatResponse.class);
        log.info(chat.getPromptRequest().getText());
        log.info(chat.getPromptRequest().generatePrompt(chat.getPromptConfiguration()));
        log.info(chatResponse.toString());
        QuizGenerationResult quizGenerationResult = new QuizGenerationResult();
        quizGenerationResult.setQuizId("quizSaved.getId()");
        quizGenerationResult.setChatResponse(chatResponse);
        return quizGenerationResult;

    }
}
