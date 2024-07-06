package com.quizguru.generates.consumer;

import com.quizguru.generates.dto.request.ChatRequest;
import com.quizguru.generates.dto.request.ContextGenerationResult;
import com.quizguru.generates.dto.request.GenerateRequest;
import com.quizguru.generates.dto.request.text.BaseTextRequest;
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
    public void generateContextConsumer(GenerateRequest generateRequest) {

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
        ContextGenerationResult result = generateService.generateContext(chat);
        log.info(result.toString());
    }

}
