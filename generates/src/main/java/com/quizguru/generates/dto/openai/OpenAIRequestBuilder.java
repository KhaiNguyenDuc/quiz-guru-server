package com.quizguru.generates.dto.openai;

import com.quizguru.generates.dto.Message;
import com.quizguru.generates.dto.AIRequestBuilder;
import com.quizguru.generates.dto.request.ChatRequest;
import com.quizguru.generates.dto.request.ResponseFormat;
import com.quizguru.generates.enums.AIProvider;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.util.List;

public class OpenAIRequestBuilder implements AIRequestBuilder {
    @Override
    public Object buildRequest(ChatRequest chatRequest) {
        return new OpenAIChatRequest(
                chatRequest.getModel(),
                List.of(new Message("user", chatRequest.getGivenText()),
                        new Message("user",chatRequest.getPromptRequest().generatePrompt(chatRequest.getPromptConfiguration()))
                ),
                new ResponseFormat("json_object")
        );
    }

    @Override
    public Class<?> getResponseType() {
        return OpenAIResponse.class;
    }

    @Override
    public AIProvider getProvider() {
        return AIProvider.OPENAI;
    }

    @Override
    public HttpHeaders buildHeaders(String apiKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        return headers;
    }
}
