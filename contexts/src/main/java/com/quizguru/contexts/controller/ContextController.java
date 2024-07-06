package com.quizguru.contexts.controller;

import com.quizguru.contexts.dto.request.GenerateRequest;
import com.quizguru.contexts.dto.response.GenerateContextResponse;
import com.quizguru.contexts.service.ContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contexts/api/v1")
@RequiredArgsConstructor
public class ContextController {

    private final ContextService contextService;

    @PostMapping("/create")
    public ResponseEntity<GenerateContextResponse> createContext(
            @RequestBody GenerateRequest generateRequest
    ){
        GenerateContextResponse response = contextService.createContext(generateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
