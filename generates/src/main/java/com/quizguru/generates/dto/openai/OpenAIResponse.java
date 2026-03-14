package com.quizguru.generates.dto.openai;

import com.quizguru.generates.dto.Message;
import com.quizguru.generates.dto.response.AIResponse;
import lombok.Data;

import java.util.List;

@Data
public class OpenAIResponse implements AIResponse {
    private List<Choice> choices;
    private String model;

    @Override
    public String getContent() {
        return choices != null && !choices.isEmpty()
                ? choices.get(0).getMessage().getContent()
                : null;
    }

    @Data
    public static class Choice {
        private Message message;
    }
}
