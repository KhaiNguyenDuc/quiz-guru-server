package com.khai.quizguru.customers.service.impl;

import com.khai.quizguru.customers.dto.request.CustomerUpdateRequest;
import com.khai.quizguru.customers.dto.response.UserResponse;
import com.khai.quizguru.customers.enums.RoleName;
import com.khai.quizguru.customers.exception.AccessDeniedException;
import com.khai.quizguru.customers.exception.ResourceNotFoundException;
import com.khai.quizguru.customers.mapper.UserMapper;
import com.khai.quizguru.customers.properties.KeycloakProperties;
import com.khai.quizguru.customers.dto.request.RegisterCredentials;
import com.khai.quizguru.customers.dto.response.RegisterResponse;
import com.khai.quizguru.customers.service.CustomerService;
import com.khai.quizguru.customers.utils.Constant;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final Keycloak keycloak;
    private final KeycloakProperties keycloakProperties;

    public static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

    @Override
    public RegisterResponse createUser(RegisterCredentials registerCredentials) {
        RealmResource realmResource = keycloak.realm(keycloakProperties.getRealm());
        String username = registerCredentials.username();
        String email = registerCredentials.email();
        CredentialRepresentation credential = createPasswordCredentials(registerCredentials.password());

        // Define user
        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setEmail(email);
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);
        Response response = realmResource.users().create(user);
        if (response.getStatus() != 201) {
            // Log the response details for debugging
            String responseBody = response.readEntity(String.class);
            System.out.println("Error response: " + responseBody);
            throw new RuntimeException("Failed to create user. Status: " + response.getStatus() + ", Response: " + responseBody);
        }
        // get new user
        String userId = CreatedResponseUtil.getCreatedId(response);
        UserResource userResource = realmResource.users().get(userId);
        RoleRepresentation guestRealmRole = realmResource.roles().get(RoleName.USER.toString()).toRepresentation();

        // Assign realm role USER to user
        userResource.roles().realmLevel().add(Collections.singletonList(guestRealmRole));

        return new RegisterResponse(userId, username, email);
    }

    @Override
    public List<String> findRoleFromUserId(String userId) {
        List<String> roles;
        UserResource userResource = keycloak.realm(keycloakProperties.getRealm()).users().get(userId);

        if (userResource != null) {
            List<RoleRepresentation> realmRoles = userResource.roles().realmLevel().listEffective();
            roles = realmRoles.stream()
                    .map(RoleRepresentation::getName)
                    .collect(Collectors.toList());
        } else {
            throw new ResourceNotFoundException(Constant.ERROR_CODE.UNAUTHORIZED_INTERNAL_ERROR, userId);
        }

        return roles;
    }

    @Override
    public UserResponse findById(String id) {
        UserResource userResource = keycloak.realm(keycloakProperties.getRealm()).users().get(id);
        if (userResource != null) {
            String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
            if (!id.equals(currentUserId)){
                throw new AccessDeniedException(Constant.ERROR_CODE.ACCESS_DENIED_MSG, "user", id);
            }
            UserRepresentation userRepresentation = userResource.toRepresentation();
            return UserMapper.toUserResponse(userRepresentation, userResource);
        } else {
            throw new ResourceNotFoundException(Constant.ERROR_CODE.UNAUTHORIZED_ID_NOT_EXIST, id);
        }
    }

    @Override
    public void updateCustomer(CustomerUpdateRequest customerUpdateRequest) {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        UserRepresentation userRepresentation =
                keycloak.realm(keycloakProperties.getRealm()).users().get(id).toRepresentation();
        if (userRepresentation != null) {
            userRepresentation.setFirstName(customerUpdateRequest.firstName());
            userRepresentation.setLastName(customerUpdateRequest.lastName());
            userRepresentation.setUsername(customerUpdateRequest.username());
            RealmResource realmResource = keycloak.realm(keycloakProperties.getRealm());
            UserResource userResource = realmResource.users().get(id);
            userResource.update(userRepresentation);
        } else {
            throw new ResourceNotFoundException(Constant.ERROR_CODE.UNAUTHORIZED_ID_NOT_EXIST, id);
        }
    }

}
