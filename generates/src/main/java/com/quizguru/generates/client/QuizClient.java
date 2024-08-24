package com.quizguru.generates.client;

import com.quizguru.generates.dto.request.QuizGenerateResult;
import com.quizguru.generates.dto.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "QUIZZES")
public interface QuizClient {

    @PutMapping("/quizzes/internal")
    ResponseEntity<ApiResponse<String>> updateQuiz(@RequestBody QuizGenerateResult quizGenerateResult);
}
