package com.quizguru.libraries.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record WordSetResponse(
        String id,
        String name,
        List<WordResponse> words,
        Integer wordNumber,
        String quizId,
        Integer reviewNumber,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss", timezone = "UTC")
        Instant createdAt
) { }

