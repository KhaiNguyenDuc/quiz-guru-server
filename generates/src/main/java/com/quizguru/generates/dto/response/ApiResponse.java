package com.quizguru.generates.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
public class ApiResponse<T> {

    private String message;
    private T data;
}
