package com.quizguru.generates.exception;

import com.quizguru.generates.dto.error.ExceptionDetails;
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

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionDetails> handleUnauthorizedException(
            UnauthorizedException ex, WebRequest request
    ){
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.UNAUTHORIZED.toString(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                ex.getMessage());

        return new ResponseEntity<>(details, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDetails> handleResourceNotFoundExceptionDetailsResponseEntity(
            ResourceNotFoundException ex, WebRequest request
    ){
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.NOT_FOUND.toString(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage());

        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }
}
