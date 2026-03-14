package com.quizguru.generates.dto.gemini;

import lombok.Data;

import java.util.List;

@Data
public class Content {
    private List<Part> parts;

    public Content(List<Part> parts) {
        this.parts = parts;
    }
}