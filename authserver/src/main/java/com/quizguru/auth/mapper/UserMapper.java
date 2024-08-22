package com.quizguru.auth.mapper;

import com.quizguru.auth.dto.response.RegisterResponse;
import com.quizguru.auth.dto.response.UserResponse;
import com.quizguru.auth.model.User;

public class UserMapper {

    public static RegisterResponse toRegisterResponse(User user){
        return RegisterResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .roles(user.getRoles())
                .imagePath("")
                .build();
    }
}
