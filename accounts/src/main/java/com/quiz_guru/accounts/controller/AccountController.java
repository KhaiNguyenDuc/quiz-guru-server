package com.quiz_guru.accounts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AccountController {

    @GetMapping
    public ResponseEntity<String> getAccountDemo(){
        return new ResponseEntity<>( "Demo account", HttpStatus.OK);
    }
}
