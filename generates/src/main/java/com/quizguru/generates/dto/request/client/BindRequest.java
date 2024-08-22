package com.quizguru.generates.dto.request.client;

import lombok.Builder;

@Builder
public record BindRequest(String quizId, String wordSetId) {

}
