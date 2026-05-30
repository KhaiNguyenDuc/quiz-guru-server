package com.quizguru.quizzes.quizmanagement.domain.model.enums;

public enum QuestionType {
    SINGLE_CHOICE("SINGLE_CHOICE"),
    MULTIPLE_CHOICE("MULTIPLE_CHOICE");

    private final String value;

    QuestionType(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}