package com.quiz_guru.problems.controller;

import com.quiz_guru.problems.dto.ProblemDto;
import com.quiz_guru.problems.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/problems/")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @GetMapping
    public ResponseEntity<ProblemDto> getProblemDemo(){
        ProblemDto problem = problemService.getProblemDemo();
        return new ResponseEntity<>(problem, HttpStatus.OK);
    }
}
