package com.quizguru.records.service.impl;

import com.quizguru.records.dto.request.RecordRequest;
import com.quizguru.records.dto.response.PageResponse;
import com.quizguru.records.dto.response.RecordResponse;
import com.quizguru.records.exception.AccessDeniedException;
import com.quizguru.records.exception.ResourceNotFoundException;
import com.quizguru.records.mapper.RecordMapper;
import com.quizguru.records.model.Record;
import com.quizguru.records.repository.RecordRepository;
import com.quizguru.records.service.RecordService;
import com.quizguru.records.utils.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;

    @Override
    public RecordResponse createRecord(RecordRequest recordRequest) {
        Record record = RecordMapper.toRecord(recordRequest);
        Record recordSaved = recordRepository.save(record);
        return RecordMapper.toRecordResponse(recordSaved);
    }

    @Override
    public RecordResponse findById(String recordId) {
        Record record = recordRepository.findById(recordId).orElseThrow(
                () -> new ResourceNotFoundException(Constant.RESOURCE_NOT_FOUND, "record", recordId)
        );
        return RecordMapper.toRecordResponse(record);
    }

    @Override
    public PageResponse<List<RecordResponse>> findAllById(String userId, Pageable pageable) {

            Page<Record> records = recordRepository.findByUserId(userId, pageable);
            List<RecordResponse> recordResponses = RecordMapper.toRecordResponses(records.getContent());

            return new PageResponse<>(
                    recordResponses,
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    records.getTotalPages(),
                    records.getNumberOfElements()
            );
    }
}
