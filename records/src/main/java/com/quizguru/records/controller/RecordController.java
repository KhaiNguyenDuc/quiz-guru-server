package com.quizguru.records.controller;

import com.quizguru.records.dto.request.RecordRequest;
import com.quizguru.records.dto.request.UpdateRecordRequest;
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
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        RecordResponse records = recordService.createRecord(recordRequest, userId);
        return new ResponseEntity<>(new ApiResponse<>(records, "success"), HttpStatus.CREATED);
    }

    @PutMapping("/internal/update")
    public String updateRecord(@RequestBody UpdateRecordRequest updateRecordRequest) {

        if(updateRecordRequest.recordId() != null && updateRecordRequest.recordId() != "") {
            recordService.updateRecord(updateRecordRequest);
            return "";
        }
        RecordRequest recordRequest = RecordRequest.builder()
                .recordItems(updateRecordRequest.recordItems())
                .quizId(updateRecordRequest.quizId())
                .timeLeft(updateRecordRequest.timeLeft())
                .build();
        RecordResponse recordResponse = recordService.createRecord(recordRequest, updateRecordRequest.userId());
        return recordResponse.id();
    }


    @GetMapping
    public ResponseEntity<ApiResponse<RecordResponse>> findByd(@RequestParam("id") String recordId) {
        RecordResponse record = recordService.findById(recordId);
        return new ResponseEntity<>(new ApiResponse<>(record, "success"), HttpStatus.OK);
    }
}
