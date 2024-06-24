package com.quiz_guru.contexts.service;

import com.quiz_guru.contexts.client.ProblemClient;
import com.quiz_guru.contexts.client.ProblemDto;
import com.quiz_guru.contexts.dto.ContextDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ContextServiceImp implements ContextService{

    private final ProblemClient problemClient;
    @Override
    public ContextDto getContextDemo() {
        ProblemDto problem = problemClient.getProblemDemo().getBody();
        return new ContextDto("1", "Context 1", "Khai", Set.of(problem));
    }
}
