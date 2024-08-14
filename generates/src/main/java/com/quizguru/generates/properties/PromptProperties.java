package com.quizguru.generates.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "prompt")
@Data
@Component
public class PromptProperties {
    public String singleChoiceQuizPrompt;
    public String multipleChoiceQuizPrompt;
    public String mixChoiceQuizPrompt;
    public String vocabularyQuizPrompt;
    public String textToVocabPrompt;
}
