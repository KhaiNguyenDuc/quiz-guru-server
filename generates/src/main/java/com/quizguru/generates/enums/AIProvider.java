package com.quizguru.generates.enums;

public enum AIProvider {
    OPENAI("openai"),
    GEMINI("gemini");

    private final String value;

    AIProvider(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static AIProvider fromString(String value) {
        for (AIProvider provider : AIProvider.values()) {
            if (provider.value.equalsIgnoreCase(value)) {
                return provider;
            }
        }
        throw new IllegalArgumentException("Unknown AI provider: " + value);
    }
}
