package com.quizguru.records.service;

import com.quizguru.records.dto.request.RecordRequest;
import com.quizguru.records.dto.response.PageResponse;
import com.quizguru.records.dto.response.RecordResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface RecordService {

    RecordResponse createRecord(RecordRequest recordRequest);

    RecordResponse findById(String recordId);

    PageResponse<List<RecordResponse>> findAllById(String userId, Pageable pageable);
}
