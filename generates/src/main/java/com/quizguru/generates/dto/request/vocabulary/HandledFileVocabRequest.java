package com.quizguru.generates.dto.request.vocabulary;

import com.quizguru.generates.properties.PromptProperties;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class HandledFileVocabRequest extends VocabularyPromptRequest {

    private String fileContent;

    @Override
    public String getText() {
       return this.fileContent;
    }

    @Override
    public String generatePrompt(PromptProperties promptConfiguration){
        String prompt = promptConfiguration.getTextToVocabPrompt();
        return String.format(prompt,
                this.getNumber(),
                this.type.getValue(),
                this.level.getValue(),
                this.getLanguage());
    }
}
