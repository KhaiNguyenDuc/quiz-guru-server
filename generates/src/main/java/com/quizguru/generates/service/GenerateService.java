package com.quizguru.generates.service;

import com.quizguru.generates.dto.request.ChatRequest;
import com.quizguru.generates.dto.response.ChatResponse;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

public interface GenerateService {
    void generateQuiz(ChatRequest chat, String userId);
    void generateWordSet(ChatRequest chat, ChatResponse chatResponse);
    void setSecurityContextFromHeaders(String userId);
}
