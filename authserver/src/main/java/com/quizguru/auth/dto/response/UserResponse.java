package com.quizguru.auth.dto.response;

import com.quizguru.auth.model.Role;
import lombok.Builder;

import java.util.List;

@Builder
public record UserResponse(String id, String username, String email, List<Role> roles, String imagePath) {
}
