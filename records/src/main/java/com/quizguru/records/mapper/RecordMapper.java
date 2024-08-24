package com.quizguru.records.mapper;

import com.quizguru.records.dto.client.ChoiceResponse;
import com.quizguru.records.dto.client.QuestionType;
import com.quizguru.records.dto.request.RecordItemRequest;
import com.quizguru.records.dto.request.RecordRequest;
import com.quizguru.records.dto.response.ProvRecordResponse;
import com.quizguru.records.dto.response.RecordItemResponse;
import com.quizguru.records.dto.response.RecordResponse;
import com.quizguru.records.model.Record;
import com.quizguru.records.model.RecordItem;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RecordMapper {
    public static List<RecordResponse> toRecordResponses(List<Record> records) {

        List<RecordResponse> recordResponses = new ArrayList<>();
        for (Record record : records) {
            RecordResponse recordResponse = RecordMapper.toRecordResponse(record);
            recordResponses.add(recordResponse);
        }
        return recordResponses;
    }

    public static RecordResponse toRecordResponse(Record record) {

        List<RecordItemResponse> recordItemResponses = new ArrayList<>();
        record.getRecordItems().forEach(
                recordItem -> {
                    recordItemResponses.add(RecordItemResponse.builder().build());
                }
        );
       return RecordResponse.builder()
                    .id(record.getId())
                    .score(record.getScore())
                    .duration(record.getDuration())
                    .timeLeft(record.getTimeLeft())
                    .quizId(record.getQuizId())
                    .recordItems(recordItemResponses)
                    .userId(record.getUserId())
                    .build();
    }

    public static Record toRecord(RecordRequest recordRequest, String userId, ProvRecordResponse provRecordResponse) {

        List<RecordItemResponse> recordItemResponses = provRecordResponse.recordItemResponses();
        List<RecordItemRequest> recordItemRequests = recordRequest.recordItems();
        List<RecordItem> recordItems = new ArrayList<>();
        Integer score = RecordMapper.getScore(recordItemResponses);
        Record record = Record.builder()
                .userId(userId)
                .timeLeft(recordRequest.timeLeft())
                .score(score)
                .duration(provRecordResponse.duration())
                .quizId(recordRequest.quizId())
                .build();
        for (RecordItemRequest recordItemRequest : recordItemRequests) {
            RecordItem recordItem = RecordItem.builder()
                    .questionId(recordItemRequest.questionId())
                    .selectedChoices(recordItemRequest.selectedChoiceIds())
                    .explanation(recordItemRequest.explanation())
                    .record(record)
                    .build();
            recordItems.add(recordItem);
        }
        record.setRecordItems(recordItems);
        return record;

    }

    private static Integer getScore(List<RecordItemResponse> recordItemResponses) {
        String singleChoiceType = QuestionType.SINGLE_CHOICE.getValue();
        String multipleChoiceType = QuestionType.MULTIPLE_CHOICE.getValue();
        AtomicInteger score = new AtomicInteger(0);

        recordItemResponses.forEach(recordItemResponse -> {
            if (recordItemResponse.question().type().equals(multipleChoiceType)) {
                boolean allCorrect = recordItemResponse.selectedChoices().stream()
                        .allMatch(ChoiceResponse::isCorrect);
                if (allCorrect) {
                    score.addAndGet(1); // Increment score only if all selected choices are correct
                }
            } else if (recordItemResponse.question().type().equals(singleChoiceType)) {
                for (ChoiceResponse choiceResponse : recordItemResponse.selectedChoices()) {
                    if (choiceResponse.isCorrect()) {
                        score.addAndGet(1); // Increment score for each correct choice
                        break;
                    }
                }
            }
        });

        return score.get();
    }

    public static RecordResponse toRecordResponseWithProvData(Record record, ProvRecordResponse provRecordResponse) {

        return RecordResponse.builder()
                .id(record.getId())
                .recordItems(provRecordResponse.recordItemResponses())
                .givenText(provRecordResponse.givenText())
                .score(record.getScore())
                .duration(record.getDuration())
                .timeLeft(record.getTimeLeft())
                .quizId(record.getQuizId())
                .userId(record.getUserId())
                .build();
    }

    public static RecordRequest toRecordRequest(Record record) {

        return RecordRequest.builder()
                .quizId(record.getQuizId())
                .timeLeft(record.getTimeLeft())
                .recordItems(RecordMapper.toRecordItemRequests(record.getRecordItems()))
                .build();

    }

    private static List<RecordItemRequest> toRecordItemRequests(List<RecordItem> recordItems) {
        List<RecordItemRequest> recordItemRequests = new ArrayList<>();
        for (RecordItem recordItem : recordItems) {

            RecordItemRequest recordItemRequest = RecordMapper.toRecordItemRequest(recordItem);
            recordItemRequests.add(recordItemRequest);
        }
        return recordItemRequests;
    }

    private static RecordItemRequest toRecordItemRequest(RecordItem recordItem) {

        return RecordItemRequest.builder()
                .explanation(recordItem.getExplanation())
                .questionId(recordItem.getQuestionId())
                .selectedChoiceIds(recordItem.getSelectedChoices())
                .build();
    }
}
