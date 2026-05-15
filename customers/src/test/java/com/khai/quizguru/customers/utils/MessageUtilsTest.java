package com.khai.quizguru.customers.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageUtilsTest {

    @Test
    void getMessage_ShouldReturnExactString_WhenNoArgumentsProvided() {
        // When
        String result = MessageUtils.getMessage(Constant.ERROR_CODE.INVALID_TOKEN);

        // Then
        assertEquals("Token invalid", result);
    }

    @Test
    void getMessage_ShouldFormatString_WhenOneArgumentProvided() {
        // Given
        String username = "testuser";

        // When
        String result = MessageUtils.getMessage(Constant.ERROR_CODE.UNAUTHORIZED_USERNAME_NOT_EXIST, username);

        // Then
        assertEquals("User with username testuser not exist", result);
    }

    @Test
    void getMessage_ShouldFormatString_WhenMultipleArgumentsProvided() {
        // Given
        String resource = "dashboard";
        String role = "USER";

        // When
        String result = MessageUtils.getMessage(Constant.ERROR_CODE.ACCESS_DENIED_MSG, resource, role);

        // Then
        assertEquals("You don't have permission to access this dashboard with USER", result);
    }

    @Test
    void getMessage_ShouldCatchExceptionAndReturnRawCode_WhenKeyIsMissing() {
        // Given
        String missingKey = "NON_EXISTENT_ERROR_CODE";

        // When
        String result = MessageUtils.getMessage(missingKey);

        // Then
        assertEquals("NON_EXISTENT_ERROR_CODE", result);
    }

    @Test
    void getMessage_ShouldCatchExceptionAndFormatRawCode_WhenKeyIsMissingButContainsPlaceholders() {
        // Given
        String missingKeyWithPlaceholder = "UNKNOWN_ERROR_FOR_USER_{}";
        String arg = "admin";

        // When
        String result = MessageUtils.getMessage(missingKeyWithPlaceholder, arg);

        // Then
        assertEquals("UNKNOWN_ERROR_FOR_USER_admin", result);
    }
}