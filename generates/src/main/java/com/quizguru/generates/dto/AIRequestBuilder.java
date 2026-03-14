package com.quizguru.generates.dto;

import com.quizguru.generates.dto.request.ChatRequest;
import com.quizguru.generates.enums.AIProvider;
import org.springframework.http.HttpHeaders;

public interface AIRequestBuilder {
    Object buildRequest(ChatRequest chatRequest);
    Class<?> getResponseType();
    AIProvider getProvider();
    HttpHeaders buildHeaders(String apiKey);
}