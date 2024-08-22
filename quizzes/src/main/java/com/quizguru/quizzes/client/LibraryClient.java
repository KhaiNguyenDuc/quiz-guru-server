package com.quizguru.quizzes.client;

import com.quizguru.quizzes.dto.response.ApiResponse;
import com.quizguru.quizzes.dto.response.WordSetResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "LIBRARIES")
public interface LibraryClient {

    @GetMapping("/libraries/internal/word-set")
    ResponseEntity<ApiResponse<WordSetResponse>> findWordSetByQuizId(@RequestParam("quizId") String quizId);

}
