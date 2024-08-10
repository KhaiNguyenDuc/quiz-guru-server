package com.quizguru.records.dto.request;

import java.util.List;

public record RecordRequest(String quizId, List<RecordItemRequest> recordItems) {
}
