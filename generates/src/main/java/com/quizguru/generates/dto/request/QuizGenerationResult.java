package com.quizguru.generates.dto.request;

import com.quizguru.generates.dto.response.ChatResponse;
import lombok.Data;

@Data
public class QuizGenerationResult {
    private ChatResponse chatResponse;
    private String quizId;
    private String wordSetId;
}