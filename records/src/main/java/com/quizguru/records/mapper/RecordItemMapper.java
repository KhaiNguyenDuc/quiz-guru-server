package com.quizguru.records.mapper;

import com.quizguru.records.dto.request.RecordItemRequest;
import com.quizguru.records.model.Record;
import com.quizguru.records.model.RecordItem;
import java.util.ArrayList;
import java.util.List;

public class RecordItemMapper {
    public static List<RecordItem> toRecordItem(Record record, List<RecordItemRequest> recordItemRequests) {
        List<RecordItem> recordItems = new ArrayList<>();
        for (RecordItemRequest recordItemRequest : recordItemRequests) {
            RecordItem recordItem = RecordItem.builder()
                    .questionId(recordItemRequest.questionId())
                    .selectedChoices(recordItemRequest.selectedChoiceIds())
                    .explanation(recordItemRequest.explanation())
                    .record(record)
                    .build();
            recordItems.add(recordItem);
        }
        return recordItems;
    }
}
