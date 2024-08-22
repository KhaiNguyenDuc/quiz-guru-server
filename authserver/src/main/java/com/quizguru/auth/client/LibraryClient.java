package com.quizguru.auth.client;

import com.quizguru.auth.dto.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("LIBRARIES")
public interface LibraryClient {

    @PostMapping("/libraries/internal/create")
    ResponseEntity<ApiResponse> create(@RequestParam("userId") String userId);
}
