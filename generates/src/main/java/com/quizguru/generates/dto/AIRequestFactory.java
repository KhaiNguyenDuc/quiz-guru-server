package com.quizguru.generates.dto;

import com.quizguru.generates.dto.gemini.GeminiRequestBuilder;
import com.quizguru.generates.dto.openai.OpenAIRequestBuilder;
import com.quizguru.generates.enums.AIProvider;

public class AIRequestFactory {
    public static AIRequestBuilder getBuilder(String aiProvider) {
        AIProvider provider = AIProvider.fromString(aiProvider);
        return switch (provider) {
            case OPENAI -> new OpenAIRequestBuilder();
            case GEMINI -> new GeminiRequestBuilder();
        };
    }
}