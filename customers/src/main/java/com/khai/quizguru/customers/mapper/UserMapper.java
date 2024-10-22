package com.khai.quizguru.customers.mapper;

import com.khai.quizguru.customers.dto.response.UserResponse;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserResponse toUserResponse(UserRepresentation userRepresentation, UserResource userResource) {
        List<RoleRepresentation> realmRoles = userResource.roles().realmLevel().listEffective();
        List<String> roles = realmRoles.stream()
                .map(RoleRepresentation::getName)
                .collect(Collectors.toList());

        return UserResponse.builder()
                .id(userRepresentation.getId())
                .email(userRepresentation.getEmail())
                .username(userRepresentation.getUsername())
                .roles(roles)
                .imagePath("")
                .build();
    }
}
