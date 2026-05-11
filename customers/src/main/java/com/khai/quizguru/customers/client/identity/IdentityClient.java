package com.khai.quizguru.customers.client.identity;

import com.khai.quizguru.customers.dto.request.RegisterCredentials;
import com.khai.quizguru.customers.dto.response.UserResponse;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface IdentityClient {
    String createUser(RegisterCredentials registerCredentials);
    List<String> getUserRoles(String userId);
    UserResponse getUserResponseById(String userId);
    UserRepresentation getUserRepresentationById(String userId);
    void updateUserResource(UserRepresentation userRepresentation, String userId);
}
