package com.quiz_guru.problems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProblemDto {
    private String id;
    private String name;
    private String description;
}
