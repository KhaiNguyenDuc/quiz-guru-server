package com.quizguru.records.service;

import com.quizguru.records.dto.request.RecordRequest;
import com.quizguru.records.dto.response.RecordResponse;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RecordService {
    List<RecordResponse> findByUserId(String userId);

    RecordResponse createRecord(RecordRequest recordRequest);

    RecordResponse findById(String recordId);
}
