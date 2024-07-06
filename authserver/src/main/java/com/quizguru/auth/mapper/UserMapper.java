package com.quizguru.auth.mapper;

import com.quizguru.auth.dto.response.RegisterResponse;
import com.quizguru.auth.model.User;

public class UserMapper {

    public static RegisterResponse userToRegisterResponse(User user){
        return RegisterResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }
}
