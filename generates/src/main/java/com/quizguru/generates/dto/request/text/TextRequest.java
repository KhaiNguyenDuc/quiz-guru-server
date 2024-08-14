package com.quizguru.generates.dto.request.text;

import com.quizguru.generates.properties.PromptProperties;
import com.quizguru.generates.dto.request.HasHtmlContent;
import com.quizguru.generates.dto.request.PromptRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class TextRequest extends PromptRequest implements HasHtmlContent {

    private String content;
    private String htmlContent;

    @Override
    public String getText() {
        return this.content;
    }

    @Override
    public String getHtmlContent() {
        return this.htmlContent;
    }

    @Override
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
