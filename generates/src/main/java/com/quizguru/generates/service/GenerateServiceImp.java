package com.quizguru.generates.service;

import com.quizguru.generates.amqp.properties.GenerateProperties;
import com.quizguru.generates.amqp.properties.PromptProperties;
import com.quizguru.generates.dto.request.ChatRequest;
import com.quizguru.generates.dto.response.ChatResponse;
import com.quizguru.generates.dto.request.ContextGenerationResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenerateServiceImp implements GenerateService{

    private final RestTemplate restTemplate;

    @Override
    public ContextGenerationResult generateContext(ChatRequest chat) {

        PromptProperties configuration = chat.getPromptConfiguration();
        GenerateProperties generateConfiguration = chat.getGenerateConfiguration();
        ChatResponse chatResponse = restTemplate.postForObject(generateConfiguration.getApiURL(), chat, ChatResponse.class);
        log.info(chat.getPromptRequest().generatePrompt(chat.getPromptConfiguration()));
        log.info(chatResponse.toString());
        ContextGenerationResult contextGenerationResult = new ContextGenerationResult();
        contextGenerationResult.setQuizId("quizSaved.getId()");
        contextGenerationResult.setChatResponse(chatResponse);
        return contextGenerationResult;

    }
}
