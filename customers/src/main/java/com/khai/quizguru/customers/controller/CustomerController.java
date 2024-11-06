package com.khai.quizguru.customers.controller;

import com.khai.quizguru.customers.dto.request.CustomerUpdateRequest;
import com.khai.quizguru.customers.dto.request.RegisterCredentials;
import com.khai.quizguru.customers.dto.response.ApiResponse;
import com.khai.quizguru.customers.dto.response.RegisterResponse;
import com.khai.quizguru.customers.dto.response.UserResponse;
import com.khai.quizguru.customers.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/internal/users/role")
    public ResponseEntity<ApiResponse> findRoleFromUserId(@RequestParam("userId") String userId){
        List<String> roleName = customerService.findRoleFromUserId(userId);
        log.info("start findRoleFromUserId");
        ApiResponse response = new ApiResponse(roleName, "Success");
        log.info(response.toString());
        log.info("end findRoleFromUserId");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/profile/current")
    public ResponseEntity<ApiResponse> findCurrentUser(){
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        UserResponse userResponse = customerService.findById(id);

        ApiResponse response = new ApiResponse(userResponse, "Success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/profile/current/update")
    public ResponseEntity<ApiResponse> updateCurrentCustomer(@RequestBody CustomerUpdateRequest customerUpdateRequest){
        customerService.updateCustomer(customerUpdateRequest);
        ApiResponse response = new ApiResponse("success", "Success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> createUser(@RequestBody RegisterCredentials registerCredentials){
        RegisterResponse registerResponse = customerService.createUser(registerCredentials);
        return new ResponseEntity<>(new ApiResponse(registerResponse, "success"), HttpStatus.CREATED);
    }
}
