package com.quizguru.problems.service;

import com.quizguru.problems.dto.ProblemDto;
import org.springframework.stereotype.Service;

@Service
public class ProblemServiceImp implements ProblemService{
    @Override
    public ProblemDto getProblemDemo() {
        return new ProblemDto("1", "Problem demo 1", "Description Problem 1");
    }
}
