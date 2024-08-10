package com.quizguru.records.controller;

import com.quizguru.records.dto.request.RecordRequest;
import com.quizguru.records.dto.response.ApiResponse;
import com.quizguru.records.dto.response.RecordResponse;
import com.quizguru.records.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @GetMapping("/users")
    public ResponseEntity<ApiResponse> findByUserId(@RequestParam("id") String userId) {
        List<RecordResponse> records = recordService.findByUserId(userId);
        return new ResponseEntity<>(new ApiResponse(records, "success"), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createRecord(@RequestBody RecordRequest recordRequest) {
        RecordResponse records = recordService.createRecord(recordRequest);
        return new ResponseEntity<>(new ApiResponse(records, "success"), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> findByd(@RequestParam("id") String recordId) {
        RecordResponse record = recordService.findById(recordId);
        return new ResponseEntity<>(new ApiResponse(record, "success"), HttpStatus.OK);
    }
}
