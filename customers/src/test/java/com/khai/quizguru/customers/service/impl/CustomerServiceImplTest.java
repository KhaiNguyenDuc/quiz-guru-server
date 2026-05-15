package com.khai.quizguru.customers.service.impl;

import com.khai.quizguru.customers.client.identity.IdentityClient;
import com.khai.quizguru.customers.dto.request.CustomerUpdateRequest;
import com.khai.quizguru.customers.dto.request.RegisterCredentials;
import com.khai.quizguru.customers.dto.response.RegisterResponse;
import com.khai.quizguru.customers.dto.response.UserResponse;
import com.khai.quizguru.customers.exception.AccessDeniedException;
import com.khai.quizguru.customers.exception.ResourceNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CustomerServiceImpl Unit Test")
class CustomerServiceImplTest {

    @Mock
    private IdentityClient identityClient;
    private CustomerServiceImpl customerService;
    private RegisterCredentials credentials;
    private CustomerUpdateRequest customerUpdateRequest;
    private UserResponse userResponse;
    private MockedStatic<SecurityContextHolder> mockedSecurityContextHolder;
    private final String userId = "98765432-1234-abcd-efgh-567890abcdef";
    private final String UNAUTHORIZED_ERROR_MESSAGE = "Can't authorize the user due to internal error";

    @BeforeEach
    void setup() {

        customerService = new CustomerServiceImpl(identityClient);
        credentials = RegisterCredentials.builder().email("john@example.com").password("password").username("john").build();
        userResponse = UserResponse.builder().id(userId).email("john@example.com").username("john").roles(List.of("USER", "ADMIN")).imagePath("example").build();
        customerUpdateRequest = new CustomerUpdateRequest(userId, "john", "example", "John");
        mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class);

    }

    private void mockSecurityContext(String authenticatedUserId) {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(authenticatedUserId);
    }

    @AfterEach
    void tearDown() {
        mockedSecurityContextHolder.close();
    }

    @Test
    void createUser_ShouldReturnCorrectlyRegisterResponse_WhenSuccessful() {

        // Given
        when(identityClient.createUser(credentials)).thenReturn(userId);

        // When
        RegisterResponse registerResponse = customerService.createUser(credentials);

        // Then
        assertNotNull(registerResponse);
        assertEquals(userId, registerResponse.id());
        assertEquals(credentials.username(), registerResponse.username());
        assertEquals(credentials.email(), registerResponse.email());
        verify(identityClient, times(1)).createUser(credentials);
    }

    @Test
    void createUser_ShouldThrowException_WhenUserAlreadyExists() {

        // Given
        String errorMessage = "User already exists in Identity Provider";
        when(identityClient.createUser(credentials))
                .thenThrow(new IllegalArgumentException(errorMessage));

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> customerService.createUser(credentials));
        assertEquals(UNAUTHORIZED_ERROR_MESSAGE + " " + errorMessage, exception.getMessage());
    }

    @Test
    void createUser_ShouldThrowException_WhenIdentityClientFails() {

        // Given
        String errorMessage = "Failed to communicate with Identity Provider";
        when(identityClient.createUser(credentials)).thenThrow(new IllegalStateException(errorMessage));

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> customerService.createUser(credentials));
        assertEquals(UNAUTHORIZED_ERROR_MESSAGE + " " + errorMessage, exception.getMessage());
    }

    @Test
    void createUser_ShouldThrowException_WhenRoleConfigurationFails() {

        // Given
        String errorMessage = "User account created but role configuration failed";
        when(identityClient.createUser(credentials)).thenThrow(new IllegalStateException(errorMessage));

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> customerService.createUser(credentials));
        assertEquals(UNAUTHORIZED_ERROR_MESSAGE + " " + errorMessage, exception.getMessage());
    }

    @Test
    void findRoleFromUserId_ShouldReturnListRole_WhenSuccessful() {

        //Given
        List<String> mockRoles = new ArrayList<>(List.of("USER", "ADMIN"));
        when(identityClient.getUserRoles(userId)).thenReturn(mockRoles);

        // When
        List<String> roles = customerService.findRoleFromUserId(userId);

        // Then
        assertNotNull(roles);
        assertEquals(roles, mockRoles);
    }

    @Test
    void findRoleFromUserId_ShouldThrowException_WhenUserNotFound() {

        //Given
        String errorMessage = "User not found in Identity Provider";
        when(identityClient.getUserRoles(userId)).thenThrow(new IllegalArgumentException(errorMessage));

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> customerService.findRoleFromUserId(userId));

        assertEquals(UNAUTHORIZED_ERROR_MESSAGE + " " + errorMessage, exception.getMessage());
    }

    @Test
    void findById_ShouldSucceed_WhenUserExists() {

        // Given
        mockSecurityContext(userId);
        when(identityClient.getUserResponseById(userId)).thenReturn(userResponse);

        // When
        UserResponse result = customerService.findById(userId);

        // Then
        assertNotNull(result);
        assertEquals(result.email(), userResponse.email());
        assertEquals(result.id(), userResponse.id());
        assertEquals(result.username(), userResponse.username());
        assertEquals(result.roles(), userResponse.roles());
        assertEquals(result.imagePath(), userResponse.imagePath());
        verify(identityClient, times(1)).getUserResponseById(userId);

    }

    @Test
    void findById_ShouldThrowException_WhenUserIdMissMatched() {

        // Given
        String anotherUserId = "testId";
        String errorMessage = String.format("You don't have permission to access this user with %s", userId);
        mockSecurityContext(anotherUserId);

        // When & Then
        AccessDeniedException exception = assertThrows(AccessDeniedException.class, () -> customerService.findById(userId));

        assertEquals(errorMessage, exception.getMessage());

    }

    @Test
    void findById_ShouldThrowException_WhenUserNotExists() {

        // Given
        String errorMessage = String.format("User with id %s not exist", userId);
        mockSecurityContext(userId);
        when(identityClient.getUserResponseById(userId)).thenThrow(new IllegalArgumentException(errorMessage));

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> customerService.findById(userId));

        assertEquals(errorMessage, exception.getMessage());

    }

    @Test
    void updateCustomer_ShouldSucceed_WhenUserExists() {

        // Given
        mockSecurityContext(userId);
        UserRepresentation userRepresentation = new UserRepresentation();
        when(identityClient.getUserRepresentationById(userId)).thenReturn(userRepresentation);

        // When
        customerService.updateCustomer(customerUpdateRequest);

        // Then
        assertEquals(userRepresentation.getFirstName(), customerUpdateRequest.firstName());
        assertEquals(userRepresentation.getLastName(), customerUpdateRequest.lastName());
        assertEquals(userRepresentation.getUsername(), customerUpdateRequest.username());
        verify(identityClient, times(1)).updateUserResource(userRepresentation, userId);
        verify(identityClient, times(1)).getUserRepresentationById(userId);

    }

    @Test
    void updateCustomer_ShouldThrowException_WhenIdDoesNotExistInIdentityClient() {

        // Given
        String errorMessage = "User not found in Identity Provider";
        mockSecurityContext(userId);
        when(identityClient.getUserRepresentationById(userId)).thenThrow(new IllegalArgumentException(errorMessage));

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> customerService.updateCustomer(customerUpdateRequest));
        assertEquals("User with id " + userId + " not exist", exception.getMessage());
        verify(identityClient, never()).updateUserResource(any(), anyString());
    }
}