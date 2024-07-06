package com.quizguru.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDetails> handleGlobalException(
            Exception exception, WebRequest request
    ) {
        ExceptionDetails exceptionDetail = new ExceptionDetails(
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exceptionDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceExistException.class)
    public ResponseEntity<ExceptionDetails> handleResourceExistException(
            Exception exception, WebRequest request
    ) {
        ExceptionDetails exceptionDetail = new ExceptionDetails(
                request.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exceptionDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnAuthorizeException.class)
    public ResponseEntity<ExceptionDetails> handleUnAuthorizeException(
            Exception exception, WebRequest request
    ) {
        ExceptionDetails exceptionDetail = new ExceptionDetails(
                request.getDescription(false),
                HttpStatus.UNAUTHORIZED,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exceptionDetail, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDetails> handleResourceNotFoundException(
            Exception exception, WebRequest request
    ) {
        ExceptionDetails exceptionDetail = new ExceptionDetails(
                request.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exceptionDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TokenValidationException.class)
    public ResponseEntity<ExceptionDetails> handleTokenValidationException(
            Exception exception, WebRequest request
    ) {
        ExceptionDetails exceptionDetail = new ExceptionDetails(
                request.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exceptionDetail, HttpStatus.BAD_REQUEST);
    }
}
