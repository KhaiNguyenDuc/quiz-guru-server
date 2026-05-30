package com.quizguru.quizzes.quizmanagement.domain.model;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {

    private String id;
    private String query;
    private List<Choice> choices;
    private String explanation;
    private com.quizguru.quizzes.quizmanagement.domain.model.enums.QuestionType type;
    private List<Integer> answers; // Not stored in the database but used for processing
    public void setAnswer(Integer answer, List<Choice> choices) {
        choices.get(answer).setIsCorrect(true);
    }
    private Quiz quiz;
}