package com.quizguru.records.controller;

import com.quizguru.records.dto.request.RecordRequest;
import com.quizguru.records.dto.response.ApiResponse;
import com.quizguru.records.dto.response.PageResponse;
import com.quizguru.records.dto.response.RecordResponse;
import com.quizguru.records.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @GetMapping("/users/current")
    public ResponseEntity<PageResponse<List<RecordResponse>>> findCurrentUserRecord(
            @RequestParam(name = "page", defaultValue ="0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size
    ){
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        PageResponse<List<RecordResponse>> records = recordService.findAllById(id, PageRequest.of(page, size));
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RecordResponse>> createRecord(@RequestBody RecordRequest recordRequest) {
        RecordResponse records = recordService.createRecord(recordRequest);
        return new ResponseEntity<>(new ApiResponse<>(records, "success"), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<RecordResponse>> findByd(@RequestParam("id") String recordId) {
        RecordResponse record = recordService.findById(recordId);
        return new ResponseEntity<>(new ApiResponse<>(record, "success"), HttpStatus.OK);
    }
}
