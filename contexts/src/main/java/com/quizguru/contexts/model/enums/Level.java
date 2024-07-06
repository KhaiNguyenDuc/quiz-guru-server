package com.quizguru.contexts.model.enums;

public enum Level {
    EASY("EASY"),
    MEDIUM("MEDIUM"),
    HARD("HARD");

    private final String value;

    Level(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
