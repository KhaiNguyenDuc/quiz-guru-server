package com.quizguru.records.service.impl;

import com.quizguru.records.client.library.LibraryClient;
import com.quizguru.records.client.quiz.QuizClient;
import com.quizguru.records.client.quiz.dto.response.ProvRecordResponse;
import com.quizguru.records.client.quiz.dto.response.RecordItemResponse;
import com.quizguru.records.dto.request.RecordRequest;
import com.quizguru.records.dto.request.UpdateRecordRequest;
import com.quizguru.records.dto.response.*;
import com.quizguru.records.exception.AccessDeniedException;
import com.quizguru.records.exception.InvalidRequestException;
import com.quizguru.records.exception.ResourceNotFoundException;
import com.quizguru.records.mapper.RecordItemMapper;
import com.quizguru.records.mapper.RecordMapper;
import com.quizguru.records.model.Record;
import com.quizguru.records.model.RecordItem;
import com.quizguru.records.repository.RecordRepository;
import com.quizguru.records.service.RecordService;
import com.quizguru.records.utils.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;
    private final QuizClient quizClient;
    private final LibraryClient libraryClient;

    @Override

    public RecordResponse createRecord(RecordRequest recordRequest, String userId) {
        try {

            ApiResponse<ProvRecordResponse> apiResponse = quizClient.findProvisionDataForRecordById(recordRequest.quizId(), recordRequest.recordItems()).getBody();
            if(recordRequest.recordId() == null || recordRequest.recordId().isEmpty()){
                libraryClient.increaseReviewTime(recordRequest.quizId());
                if(Objects.nonNull(apiResponse)){
                    ProvRecordResponse provRecordResponse = apiResponse.data();
                    Record record = RecordMapper.toRecord(recordRequest, userId, provRecordResponse);
                    Record recordSaved = recordRepository.save(record);
                    return RecordMapper.toRecordResponseWithProvData(recordSaved, provRecordResponse);
                }
            }else{
                Record record = recordRepository.findById(recordRequest.recordId())
                        .orElseThrow(() -> new ResourceNotFoundException(Constant.ERROR_CODE.RESOURCE_NOT_FOUND, "record", recordRequest.recordId()));
                if(!userId.equals(record.getUserId())){
                    throw new AccessDeniedException(Constant.ERROR_CODE.ACCESS_DENIED, "user", userId);
                }
                if(Objects.nonNull(apiResponse)){
                    ProvRecordResponse provRecordResponse = apiResponse.data();
                    Record recordSaved = recordRepository.save(record);
                    return RecordMapper.toRecordResponseWithProvData(recordSaved, provRecordResponse);
                }
            }



        } catch (ResponseStatusException ex) {
            throw new InvalidRequestException(Constant.ERROR_CODE.INVALID_REQUEST, ex.getMessage());
        } catch (Exception ex){
            throw new InvalidRequestException(Constant.ERROR_CODE.INVALID_REQUEST, ex.getMessage());
        }
        throw new InvalidRequestException(Constant.ERROR_CODE.INVALID_REQUEST, "Null result from quiz services");
    }

    @Override
    public RecordResponse findById(String recordId) {
        Record record = recordRepository.findById(recordId).orElseThrow(
                () -> new ResourceNotFoundException(Constant.ERROR_CODE.RESOURCE_NOT_FOUND, "record", recordId)
        );
        RecordRequest recordRequest = RecordMapper.toRecordRequest(record);
        try{
            ApiResponse<ProvRecordResponse> apiResponse = quizClient.findProvisionDataForRecordById(record.getQuizId(), recordRequest.recordItems()).getBody();
            if(Objects.nonNull(apiResponse)){
                ProvRecordResponse provRecordResponse = apiResponse.data();
                return RecordMapper.toRecordResponseWithProvData(record, provRecordResponse);
            }
        } catch (ResponseStatusException ex) {
            throw new InvalidRequestException(Constant.ERROR_CODE.INVALID_REQUEST, ex.getMessage());
        }
        throw new InvalidRequestException(Constant.ERROR_CODE.INVALID_REQUEST, "Null result from quiz services");

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

    @Override
    public void updateRecord(UpdateRecordRequest updateRecordRequest) {
        ApiResponse<ProvRecordResponse> apiResponse = quizClient.findProvisionDataForRecordById(updateRecordRequest.quizId(), updateRecordRequest.recordItems()).getBody();
        int score = 0;
        if(Objects.nonNull(apiResponse)){
            ProvRecordResponse provRecordResponse = apiResponse.data();
            List<RecordItemResponse> recordItemResponses = provRecordResponse.recordItemResponses();
            score = RecordMapper.getScore(recordItemResponses);
        }
        Record record = recordRepository.findById(updateRecordRequest.recordId())
                .orElseThrow(() -> new ResourceNotFoundException(Constant.ERROR_CODE.RESOURCE_NOT_FOUND, "record", updateRecordRequest.recordId()));
        List<RecordItem> recordItems = record.getRecordItems();
        List<RecordItem> recordItemNew = RecordItemMapper.toRecordItem(record, updateRecordRequest.recordItems());
        recordItems.addAll(recordItemNew);
        record.setRecordItems(recordItems);
        record.setTimeLeft(updateRecordRequest.timeLeft());
        if(score != 0){
            record.setScore(record.getScore() + score);
        }
        recordRepository.save(record);
    }
}
