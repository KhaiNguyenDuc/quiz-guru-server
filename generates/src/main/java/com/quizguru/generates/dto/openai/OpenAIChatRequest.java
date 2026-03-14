package com.quizguru.generates.dto.openai;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.quizguru.generates.dto.Message;
import com.quizguru.generates.dto.request.ResponseFormat;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class OpenAIChatRequest {
    private String model;
    private List<Message> messages;
    @JsonProperty("response_format")
    private ResponseFormat responseFormat;
}
