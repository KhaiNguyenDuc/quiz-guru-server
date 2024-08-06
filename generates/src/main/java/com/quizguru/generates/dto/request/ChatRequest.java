package com.quizguru.generates.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.quizguru.generates.amqp.properties.GenerateProperties;
import com.quizguru.generates.amqp.properties.PromptProperties;
import com.quizguru.generates.dto.Message;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO class representing a chat request.
 */
@Data
public class ChatRequest {

    private String model;
    private List<Message> messages = new ArrayList<>();

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
        this.generateMessages(givenText);
        this.model = generateProperties.model;
    }

    /**
     * Generates messages based on the given text and prompt request.
     * @param givenText The given text.
     */
    private void generateMessages(String givenText){
        this.messages.add(new Message("user", givenText));
        this.messages.add(new Message("user",this.promptRequest.generatePrompt(this.promptConfiguration)));
    }

    /**
     * Adds a message to the list of messages.
     * @param message The message to add.
     */
    public void addMessages(Message message){
        this.messages.add(message);
    }
}
