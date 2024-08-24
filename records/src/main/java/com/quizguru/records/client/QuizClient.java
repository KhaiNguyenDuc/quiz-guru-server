package com.quizguru.records.client;

import com.quizguru.records.dto.request.RecordRequest;
import com.quizguru.records.dto.response.ApiResponse;
import com.quizguru.records.dto.response.ProvRecordResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "QUIZZES")
public interface QuizClient {

    @PostMapping("/quizzes/internal/prov/record")
    ResponseEntity<ApiResponse<ProvRecordResponse>> findProvisionDataForRecordById(
            @RequestParam("id") String quizId,
            @RequestBody RecordRequest recordRequest);

}
