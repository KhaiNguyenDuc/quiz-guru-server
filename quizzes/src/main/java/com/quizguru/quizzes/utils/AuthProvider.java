package com.quizguru.quizzes.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthProvider {
    public static String getUserId(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
