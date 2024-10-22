package com.khai.quizguru.customers.dto.request;

import lombok.Builder;

@Builder
public record CustomerUpdateRequest(String id, String firstName, String lastName, String username) {
}
