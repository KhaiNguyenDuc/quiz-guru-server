package com.quizguru.contexts.client;

import com.quizguru.contexts.dto.request.GenerateRequest;
import com.quizguru.contexts.dto.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(value = "generates")
public interface GenerateClient {

    @PostMapping("/generates/api/v1/text")
    ResponseEntity<ApiResponse<Object>> generateContextByText(
            @RequestBody GenerateRequest generateRequest);
}
