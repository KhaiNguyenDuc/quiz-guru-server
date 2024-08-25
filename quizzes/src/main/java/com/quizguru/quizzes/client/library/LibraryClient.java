package com.quizguru.quizzes.client.library;

import com.quizguru.quizzes.dto.response.ApiResponse;
import com.quizguru.quizzes.client.library.dto.response.WordSetResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "LIBRARIES")
public interface LibraryClient {

    @GetMapping("/libraries/internal/word-set")
    ResponseEntity<ApiResponse<WordSetResponse>> findWordSetByQuizId(@RequestParam("quizId") String quizId);

    @DeleteMapping("libraries/internal/remove/word-set")
    ResponseEntity<ApiResponse<String>> removeQuiz(@RequestParam("quizId") String quizId);
}
