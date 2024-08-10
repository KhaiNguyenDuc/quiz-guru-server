package com.quizguru.generates.dto.request;

import com.quizguru.generates.amqp.properties.PromptProperties;
import com.quizguru.generates.enums.Level;
import com.quizguru.generates.enums.QuizType;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract class PromptRequest {

    public QuizType type;
    public Integer number;
    public String language;
    public Level level;
    public Integer duration;

    public abstract String getText();


    public abstract String generatePrompt(PromptProperties promptConfiguration);
}
