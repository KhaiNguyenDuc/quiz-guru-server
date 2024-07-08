package com.quizguru.generates.dto.error;

import java.util.ArrayList;
import java.util.List;

public record ExceptionDetails(String statusCode, String title, String detail, List<String> fieldErrors) {
    public ExceptionDetails(String statusCode, String title, String detail) {
        this(statusCode, title, detail, new ArrayList<>());
    }
}
