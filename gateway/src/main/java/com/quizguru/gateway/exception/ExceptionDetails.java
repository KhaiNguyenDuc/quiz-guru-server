package com.quizguru.gateway.exception;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record ExceptionDetails(String statusCode, String title, String detail, List<String> fieldErrors) {
    public ExceptionDetails(String statusCode, String title, String detail) {
        this(statusCode, title, detail, new ArrayList<>());
    }

    Map<String, Object> toMap(HttpStatus status) {
        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("status", status.value());
        errorAttributes.put("title", title());
        errorAttributes.put("detail", detail());
        return errorAttributes;
    }
}