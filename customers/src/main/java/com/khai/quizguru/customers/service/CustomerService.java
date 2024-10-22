package com.khai.quizguru.customers.service;

import com.khai.quizguru.customers.dto.request.CustomerUpdateRequest;
import com.khai.quizguru.customers.dto.request.RegisterCredentials;
import com.khai.quizguru.customers.dto.response.RegisterResponse;
import com.khai.quizguru.customers.dto.response.UserResponse;

import java.util.List;

public interface CustomerService {
    RegisterResponse createUser(RegisterCredentials registerCredentials);

    List<String> findRoleFromUserId(String userId);

    UserResponse findById(String name);

    void updateCustomer(CustomerUpdateRequest customerUpdateRequest);
}
