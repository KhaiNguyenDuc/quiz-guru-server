package com.quizguru.users.dto;

import lombok.Builder;

@Builder
public record EmailRequest(
        String to,
        String subject,
        String userId,
        String body
) {
}