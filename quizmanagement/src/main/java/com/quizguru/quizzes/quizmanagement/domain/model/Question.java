package com.quizguru.quizzes.quizmanagement.domain.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Question implements Serializable {

    private String id;
    private String query;
    private List<Choice> choices;
    private String explanation;
    private com.quizguru.quizzes.quizmanagement.domain.model.enums.QuestionType type;
    private List<Integer> answers;
    private Quiz quiz;

    public void setAnswer(Integer answer, List<Choice> choices) {
        choices.get(answer).setIsCorrect(true);
    }
}