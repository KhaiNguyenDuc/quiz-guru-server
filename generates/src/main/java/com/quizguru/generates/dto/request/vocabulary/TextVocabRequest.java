package com.quizguru.generates.dto.request.vocabulary;

import com.quizguru.generates.properties.PromptProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class TextVocabRequest extends VocabularyPromptRequest {
    protected String content = "";

    @Override
    public String getText() {
        return this.content;
    }

    public String generatePrompt(PromptProperties promptConfiguration){
        String prompt = promptConfiguration.getTextToVocabPrompt();
        return String.format(prompt,
                this.getNumber(),
                this.type.getValue(),
                this.level.getValue(),
                this.getLanguage());
    }
}
