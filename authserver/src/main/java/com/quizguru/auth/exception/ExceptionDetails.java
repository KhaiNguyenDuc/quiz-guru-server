package com.quizguru.auth.exception;

import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.time.LocalDateTime;

public record ExceptionDetails(String apiPath, HttpStatus errorCode, String errorMessage, LocalDateTime errorTime) {
}