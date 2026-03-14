package com.quizguru.generates.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.quizguru.generates.properties.GenerateProperties;
import com.quizguru.generates.properties.PromptProperties;
import com.quizguru.generates.dto.Message;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO class representing a chat request.
 */
@Data
@NoArgsConstructor
public class ChatRequest {

    private String model;

    @JsonIgnore
    private PromptRequest promptRequest;

    @JsonIgnore
    private String givenText = "";

    @JsonIgnore
    private PromptProperties promptConfiguration;

    @JsonIgnore
    private GenerateProperties generateConfiguration;

    @JsonProperty("response_format")
    private ResponseFormat responseFormat = new ResponseFormat("json_object");

    public ChatRequest(PromptRequest promptRequest, PromptProperties promptProperties, GenerateProperties generateProperties) {
        this.generateConfiguration = generateProperties;
        this.promptConfiguration = promptProperties;
        this.promptRequest = promptRequest;
        this.givenText = this.promptRequest.getText();
        GenerateProperties.Provider providerConfig = generateProperties.resolve(generateProperties.getProvider());
        this.model = providerConfig.getModel();
    }

}
