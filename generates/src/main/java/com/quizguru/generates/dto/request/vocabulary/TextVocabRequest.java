package com.quizguru.generates.dto.request.vocabulary;

import com.quizguru.generates.amqp.properties.PromptProperties;
import com.quizguru.generates.dto.request.PromptRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class TextVocabRequest extends PromptRequest {
    protected Integer numberOfWords;
    protected String wordSetId;
    protected String wordSetName = "";
    protected Boolean isDoQuiz = false;
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
