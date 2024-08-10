package com.quizguru.records.mapper;

import com.quizguru.records.dto.request.RecordItemRequest;
import com.quizguru.records.dto.request.RecordRequest;
import com.quizguru.records.dto.response.RecordResponse;
import com.quizguru.records.model.Record;
import com.quizguru.records.model.RecordItem;

import java.util.ArrayList;
import java.util.List;

public class RecordMapper {
    public static List<RecordResponse> recordsToRecordResponses(List<Record> records) {

        List<RecordResponse> recordResponses = new ArrayList<>();
        for (Record record : records) {
            RecordResponse recordResponse = RecordResponse.builder()
                    .id(record.getId())
                    .score(record.getScore())
                    .duration(record.getDuration())
                    .timeLeft(record.getTimeLeft())
                    .quizId(record.getQuizId())
                    .userId(record.getUserId())
                    .build();
            recordResponses.add(recordResponse);
        }
        return recordResponses;
    }

    public static RecordResponse recordToRecordResponse(Record record) {

       return RecordResponse.builder()
                    .id(record.getId())
                    .score(record.getScore())
                    .duration(record.getDuration())
                    .timeLeft(record.getTimeLeft())
                    .quizId(record.getQuizId())
                    .userId(record.getUserId())
                    .build();
    }

    public static Record recordRequestToRecord(RecordRequest recordRequest) {

        List<RecordItemRequest> recordItemRequests = recordRequest.recordItems();
        List<RecordItem> recordItems = new ArrayList<>();
        for (RecordItemRequest recordItemRequest : recordItemRequests) {
            RecordItem recordItem = RecordItem.builder()
                    .questionId(recordItemRequest.questionId())
                    .selectedChoices(recordItemRequest.selectedChoiceId())
                    .explanation(recordItemRequest.explanation())
                    .build();
            recordItems.add(recordItem);
        }
        return Record.builder()
                .quizId(recordRequest.quizId())
                .recordItems(recordItems)
                .build();

    }
}
