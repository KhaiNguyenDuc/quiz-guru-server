package com.quizguru.generates.dto.gemini;

import com.quizguru.generates.dto.AIRequestBuilder;
import com.quizguru.generates.dto.openai.OpenAIRequestBuilder;
import com.quizguru.generates.dto.request.ChatRequest;
import com.quizguru.generates.enums.AIProvider;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.util.List;

public class GeminiRequestBuilder implements AIRequestBuilder {
    @Override
    public Object buildRequest(ChatRequest chatRequest) {
        return new GeminiChatRequest(
                List.of(new Content(List.of(
                        new Part(chatRequest.getGivenText()),
                        new Part(chatRequest.getPromptRequest().generatePrompt(chatRequest.getPromptConfiguration())))))
        );
    }

    @Override
    public Class<?> getResponseType() {
        return GeminiResponse.class;
    }

    @Override
    public AIProvider getProvider() {
        return AIProvider.GEMINI;
    }

    @Override
    public HttpHeaders buildHeaders(String apiKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-goog-api-key", apiKey);
        return headers;
    }
}
