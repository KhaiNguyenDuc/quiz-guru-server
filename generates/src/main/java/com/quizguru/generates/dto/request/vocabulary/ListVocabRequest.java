package com.quizguru.generates.dto.request.vocabulary;

import com.quizguru.generates.properties.PromptProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class ListVocabRequest extends VocabularyPromptRequest {
    List<String> names = new ArrayList<>();

    @Override
    public String getText() {
        return this.names.toString();
    }

    public String generatePrompt(PromptProperties promptConfiguration){
        String prompt = promptConfiguration.getVocabularyQuizPrompt();
        return String.format(prompt,
                this.getNumber(),
                this.type.getValue(),
                this.level.getValue(),
                this.getLanguage());
    }
}
