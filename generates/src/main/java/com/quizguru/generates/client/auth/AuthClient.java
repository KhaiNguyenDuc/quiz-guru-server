package com.quizguru.generates.client.auth;

import com.quizguru.generates.dto.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "AUTH-SERVER")
public interface AuthClient {

    @GetMapping("/auth/internal/users/role")
    ResponseEntity<ApiResponse<String>> findRoleFromUserId(@RequestParam("userId") String userId);
}
