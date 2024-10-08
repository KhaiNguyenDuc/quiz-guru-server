package com.quizguru.quizzes.model.enums;

public enum QuizType {
    SINGLE_CHOICE_QUESTION("SINGLE_CHOICE"),
    MULTIPLE_CHOICE_QUESTION("MULTIPLE_CHOICE"),
    SINGLE_CHOICE_AND_MULTIPLE_CHOICE("SINGLE_CHOICE_AND_MULTIPLE_CHOICE");

    private final String value;

    QuizType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}