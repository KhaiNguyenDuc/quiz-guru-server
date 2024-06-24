package com.quiz_guru.contexts.controller;

import com.quiz_guru.contexts.configuration.ContextConfiguration;
import com.quiz_guru.contexts.dto.ContextDto;
import com.quiz_guru.contexts.service.ContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contexts/api/v1/")
@RequiredArgsConstructor
public class ContextController {

    private final ContextService contextService;
    private final ContextConfiguration contextConfiguration;

    @GetMapping()
    public ResponseEntity<ContextDto> getContextDemo(){
        ContextDto context = contextService.getContextDemo();
        return new ResponseEntity<>(context, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<String> getProfile(){
        String profile = contextConfiguration.getProfile();
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }
}
