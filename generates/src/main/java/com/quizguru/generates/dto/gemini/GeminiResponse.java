package com.quizguru.generates.dto.gemini;

import com.quizguru.generates.dto.response.AIResponse;
import lombok.Data;

import java.util.List;

@Data
public class GeminiResponse implements AIResponse {
    private List<Candidate> candidates;
    private String model;

    @Override
    public String getContent() {
        return candidates != null && !candidates.isEmpty()
                ? candidates.get(0).getContent().getParts().get(0).getText()
                : null;
    }

    @Data
    public static class Candidate {
        private Content content;
    }

    @Data
    public static class Content {
        private List<Part> parts;
    }

    @Data
    public static class Part {
        private String text;
    }
}
