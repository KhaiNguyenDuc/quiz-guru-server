package com.quizguru.records.service.impl;

import com.quizguru.records.dto.request.RecordRequest;
import com.quizguru.records.dto.response.RecordResponse;
import com.quizguru.records.exception.AccessDeniedException;
import com.quizguru.records.exception.ResourceNotFoundException;
import com.quizguru.records.mapper.RecordMapper;
import com.quizguru.records.model.Record;
import com.quizguru.records.repository.RecordRepository;
import com.quizguru.records.service.RecordService;
import com.quizguru.records.utils.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;
    @Override
    public List<RecordResponse> findByUserId(String userId) {
        if(recordRepository.existsByUserId(userId)){
            List<Record> records = recordRepository.findByUserId(userId);
            if(!records.isEmpty()){
                records.forEach(record -> {
                    if(!record.getUserId().equals(userId)){
                        throw new AccessDeniedException(Constant.ACCESS_DENIED, "record", record.getId());
                    }
                });
            }
            return RecordMapper.recordsToRecordResponses(records);
        }
        throw new ResourceNotFoundException(Constant.RESOURCE_NOT_FOUND, "user", userId);
    }

    @Override
    public RecordResponse createRecord(RecordRequest recordRequest) {
        Record record = RecordMapper.recordRequestToRecord(recordRequest);
        Record recordSaved = recordRepository.save(record);
        return RecordMapper.recordToRecordResponse(recordSaved);
    }

    @Override
    public RecordResponse findById(String recordId) {
        Record record = recordRepository.findById(recordId).orElseThrow(
                () -> new ResourceNotFoundException(Constant.RESOURCE_NOT_FOUND, "record", recordId)
        );
        return RecordMapper.recordToRecordResponse(record);
    }
}
