package com.quizguru.generates.service;

import com.quizguru.generates.dto.request.ChatRequest;
import com.quizguru.generates.dto.request.ContextGenerationResult;

public interface GenerateService {

    ContextGenerationResult generateContext(ChatRequest chat);

}
