package com.quizguru.generates.service;

import com.quizguru.generates.dto.request.ChatRequest;
import com.quizguru.generates.dto.request.QuizGenerationResult;

public interface GenerateService {
    QuizGenerationResult generateQuiz(ChatRequest chat);
}
