package com.quizguru.auth.dto.request;

import lombok.Builder;

@Builder
public record EmailRequest(
    String to,
    String subject,
    String userId,
    String body
) {
}
