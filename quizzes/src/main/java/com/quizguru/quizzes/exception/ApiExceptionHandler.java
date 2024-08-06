package com.quizguru.quizzes.exception;

import com.quizguru.quizzes.dto.error.ExceptionDetails;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ExceptionDetails> invalidExceptionHandler(
            InvalidRequestException ex, WebRequest request
    ){
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.toString(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage());

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDetails> resourceNotFoundHandler(
            ResourceNotFoundException ex, WebRequest request
    ){
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.toString(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage());

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionDetails> accessDeniedHandler(
            AccessDeniedException ex, WebRequest request
    ){
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.FORBIDDEN.toString(),
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                ex.getMessage());

        return new ResponseEntity<>(details, HttpStatus.FORBIDDEN);
    }
}
