package com.khai.quizguru.customers.service.impl;

import com.khai.quizguru.customers.client.identity.IdentityClient;
import com.khai.quizguru.customers.dto.request.RegisterCredentials;
import com.khai.quizguru.customers.dto.response.RegisterResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CustomerServiceImpl Unit Test")
class CustomerServiceImplTest {

    @Mock
    private IdentityClient identityClient;
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setup(){
        customerService = new CustomerServiceImpl(identityClient);
    }

    @Test
    void createUser_Success_ShouldReturnCorrectlyRegisterResponse(){

        // Given
        RegisterCredentials credentials = new RegisterCredentials("khai", "khai@test.com", "password123");
        String mockGeneratedId = "98765432-1234-abcd-efgh-567890abcdef";
        when(identityClient.createUser(credentials)).thenReturn(mockGeneratedId);

        // When
        RegisterResponse registerResponse = customerService.createUser(credentials);

        // Then
        assertNotNull(registerResponse);
        assertEquals(mockGeneratedId, registerResponse.id());
        assertEquals(credentials.username(), registerResponse.username());
        assertEquals(credentials.email(), registerResponse.email());
        verify(identityClient, times(1)).createUser(credentials);

    }
}