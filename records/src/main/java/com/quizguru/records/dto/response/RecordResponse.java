package com.quizguru.records.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.quizguru.records.client.quiz.dto.response.RecordItemResponse;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record RecordResponse(
        String id,
        Integer score,
        Integer duration,
        Integer timeLeft,
        String userId,
        String quizId,
         List<RecordItemResponse> recordItems, String givenText,
         @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss", timezone = "UTC")
         Instant createdAt
) { }
