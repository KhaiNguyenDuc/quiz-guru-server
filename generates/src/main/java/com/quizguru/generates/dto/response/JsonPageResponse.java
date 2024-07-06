package com.quizguru.generates.dto.response;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonPageResponse<T>  {

    private List<T> data;
    private String message;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean last;


}

