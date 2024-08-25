package com.quizguru.generates.client.library;

import com.quizguru.generates.client.library.dto.request.BindRequest;
import com.quizguru.generates.client.library.dto.request.WordSetRequest;
import com.quizguru.generates.dto.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "LIBRARIES")
public interface LibraryClient {

    @PostMapping("/libraries/word-set")
    ResponseEntity<ApiResponse<String>> createWordSet(@RequestBody WordSetRequest wordSetRequest);

    @PostMapping("/libraries/internal/word-set/bind")
    ResponseEntity<ApiResponse<String>> bindQuiz(@RequestBody BindRequest bindRequest);

    @PostMapping("/libraries/internal/word-set/add")
    ResponseEntity<ApiResponse<String>> addWordToWordSet(@RequestBody WordSetRequest wordSetRequest);
}
