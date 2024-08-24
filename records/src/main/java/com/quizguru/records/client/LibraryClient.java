package com.quizguru.records.client;

import com.quizguru.records.dto.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "LIBRARIES")
public interface LibraryClient {

    @PutMapping("/libraries/internal/review")
    ResponseEntity<ApiResponse<String>> increaseReviewTime(@RequestParam("quizId") String quizId);
}
