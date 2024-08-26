package com.quizguru.quizzes.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.Instant;
import java.time.LocalDateTime;

@Builder
public record QuizResponse(
        String id,
        Integer problemNumber,
        String level,
        Integer duration,
        String type,
        String language,
        String givenText,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss", timezone = "UTC")
        Instant createdAt
) { }
