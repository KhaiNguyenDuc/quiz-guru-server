package com.quizguru.generates.client.library.dto.request;

import lombok.Builder;

@Builder
public record BindRequest(String quizId, String wordSetId) {

}
