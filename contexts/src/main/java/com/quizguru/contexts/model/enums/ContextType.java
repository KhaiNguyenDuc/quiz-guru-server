package com.quizguru.contexts.model.enums;

public enum ContextType {
    SINGLE_CHOICE_QUESTION("SINGLE_CHOICE"),
    MULTIPLE_CHOICE_QUESTION("MULTIPLE_CHOICE"),
    MIX_QUESTION("SINGLE_CHOICE and MULTIPLE_CHOICE");

    private final String value;

    ContextType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}