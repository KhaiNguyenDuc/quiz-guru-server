package com.quizguru.auth.dto.error;

import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record ExceptionDetails(String statusCode, String title, String detail, List<String> fieldErrors) {
    public ExceptionDetails(String statusCode, String title, String detail) {
        this(statusCode, title, detail, new ArrayList<>());
    }
}