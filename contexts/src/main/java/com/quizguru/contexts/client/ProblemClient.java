package com.quizguru.contexts.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "problems")
public interface ProblemClient {

    @GetMapping("/api/v1/problems/")
    ResponseEntity<ProblemDto> getProblemDemo();
}
