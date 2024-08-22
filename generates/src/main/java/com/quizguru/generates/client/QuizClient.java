package com.quizguru.generates.client;

import com.quizguru.generates.dto.request.QuizGenerateResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "QUIZZES")
public interface QuizClient {

    @PutMapping("/quizzes/internal/create")
    void updateQuiz(@RequestBody QuizGenerateResult quizGenerateResult);
}
