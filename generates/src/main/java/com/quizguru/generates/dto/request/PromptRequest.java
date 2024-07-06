package com.quizguru.generates.dto.request;

import com.quizguru.generates.amqp.properties.PromptProperties;
import com.quizguru.generates.enums.Level;
import com.quizguru.generates.enums.ContextType;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract class PromptRequest {

    public ContextType type;
    public Integer number;
    public String language;
    public Level level;
    public Integer duration;

    public abstract String getText();


    public String generatePrompt(PromptProperties promptConfiguration){
        String prompt = "";
        prompt = switch (this.type){
            case MULTIPLE_CHOICE_QUESTION -> promptConfiguration.getMultipleChoiceQuizPrompt();
            case MIX_QUESTION -> promptConfiguration.getMixChoiceQuizPrompt();
            default -> promptConfiguration.getSingleChoiceQuizPrompt();
        };

        return String.format(prompt,
                this.getNumber(),
                this.type.getValue(),
                this.level.getValue(),
                this.getLanguage());
    }
}
