package com.khai.quizguru.customers.dto.request;

import lombok.Builder;

@Builder
public record EmailRequest(
    String to,
    String subject,
    String userId,
    String body
) {
}
