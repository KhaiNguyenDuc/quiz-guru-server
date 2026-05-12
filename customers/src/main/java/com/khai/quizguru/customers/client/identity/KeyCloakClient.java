package com.khai.quizguru.customers.client.identity;

import com.khai.quizguru.customers.dto.request.RegisterCredentials;
import com.khai.quizguru.customers.dto.response.UserResponse;
import com.khai.quizguru.customers.enums.RoleName;
import com.khai.quizguru.customers.client.mapper.UserMapper;
import com.khai.quizguru.customers.properties.KeycloakProperties;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class KeyCloakClient implements IdentityClient {

    private final Keycloak keycloak;
    private final KeycloakProperties keycloakProperties;

    private CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

    private UserRepresentation buildUser(RegisterCredentials registerCredentials){
        UserRepresentation user = new UserRepresentation();
        user.setUsername(registerCredentials.username());
        user.setEmail(registerCredentials.email());
        user.setCredentials(Collections.singletonList(createPasswordCredentials(registerCredentials.password())));
        user.setEnabled(true);
        return user;
    }

    @Override
    public String createUser(RegisterCredentials registerCredentials) {


        RealmResource realmResource = keycloak.realm(keycloakProperties.getRealm());
        Response response = realmResource.users().create(buildUser(registerCredentials));
        if (response.getStatus() == 409) {
            log.warn("Attempted to create user that already exists: {}", registerCredentials.email());
            throw new IllegalArgumentException("User already exists in Identity Provider");
        }
        if (response.getStatus() != 201) {
            String responseBody = response.readEntity(String.class);
            log.error("Failed to create user in Keycloak. Status: {}, Response: {}", response.getStatus(), responseBody);
            throw new IllegalStateException("Failed to communicate with Identity Provider");
        }
        try{
            String userId = CreatedResponseUtil.getCreatedId(response);
            UserResource userResource = realmResource.users().get(userId);
            RoleRepresentation guestRealmRole = realmResource.roles().get(RoleName.USER.toString()).toRepresentation();
            userResource.roles().realmLevel().add(Collections.singletonList(guestRealmRole));
            return userId;
        } catch (jakarta.ws.rs.NotFoundException e) {
            throw new IllegalStateException("User account created but role configuration failed");
        }
    }

    @Override
    public List<String> getUserRoles(String userId) {
        try {
            UserResource userResource = keycloak.realm(keycloakProperties.getRealm()).users().get(userId);

            List<RoleRepresentation> realmRoles = userResource.roles().realmLevel().listEffective();

            return realmRoles.stream()
                    .map(RoleRepresentation::getName)
                    .toList();

        } catch (jakarta.ws.rs.NotFoundException e) {
            throw new IllegalArgumentException("User not found in Identity Provider");
        }
    }

    @Override
    public UserResponse getUserResponseById(String userId) {
        try {
            UserResource userResource = keycloak.realm(keycloakProperties.getRealm()).users().get(userId);
            UserRepresentation userRepresentation = userResource.toRepresentation();
            return UserMapper.toUserResponse(userRepresentation, userResource);
        } catch (jakarta.ws.rs.NotFoundException e) {
            throw new IllegalArgumentException("User not found in Identity Provider");
        }
    }

    @Override
    public UserRepresentation getUserRepresentationById(String userId) {
        try {
            return keycloak.realm(keycloakProperties.getRealm()).users().get(userId).toRepresentation();
        } catch (jakarta.ws.rs.NotFoundException e) {
            throw new IllegalArgumentException("User not found in Identity Provider");
        }
    }

    @Override
    public void updateUserResource(UserRepresentation userRepresentation, String userId) {
        RealmResource realmResource = keycloak.realm(keycloakProperties.getRealm());
        UserResource userResource = realmResource.users().get(userId);
        userResource.update(userRepresentation);
    }
}
