package com.quiz_guru.contexts.dto;

import com.quiz_guru.contexts.client.ProblemDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class ContextDto {
    private String id;
    private String name;
    private String author;
    private Set<ProblemDto> problems;
}
