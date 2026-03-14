package com.quizguru.generates.dto.gemini;

import lombok.Data;

@Data
public class Part {
    private String text;

    public Part(String text) {
        this.text = text;
    }
}