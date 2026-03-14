package com.quizguru.generates.dto.gemini;

import lombok.Data;

import java.util.List;

@Data
public class GeminiChatRequest {
    private List<Content> contents;

    public GeminiChatRequest(List<Content> contents) {
        this.contents = contents;
    }
}