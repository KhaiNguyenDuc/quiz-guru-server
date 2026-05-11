package com.khai.quizguru.customers.service.impl;

import com.khai.quizguru.customers.client.identity.IdentityClient;
import com.khai.quizguru.customers.dto.request.CustomerUpdateRequest;
import com.khai.quizguru.customers.dto.response.UserResponse;
import com.khai.quizguru.customers.exception.AccessDeniedException;
import com.khai.quizguru.customers.exception.ResourceNotFoundException;
import com.khai.quizguru.customers.dto.request.RegisterCredentials;
import com.khai.quizguru.customers.dto.response.RegisterResponse;
import com.khai.quizguru.customers.service.CustomerService;
import com.khai.quizguru.customers.utils.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final IdentityClient identityClient;

    @Override
    public RegisterResponse createUser(RegisterCredentials registerCredentials) {
        try {
            String userId = identityClient.createUser(registerCredentials);
            String username = registerCredentials.username();
            String email = registerCredentials.email();
            return new RegisterResponse(userId, username, email);
        } catch (IllegalStateException | IllegalArgumentException e) {
            throw new ResourceNotFoundException(Constant.ERROR_CODE.UNAUTHORIZED_INTERNAL_ERROR);
        }
    }

    @Override
    public List<String> findRoleFromUserId(String userId) {
        try {
            return identityClient.getUserRoles(userId);
        } catch(IllegalArgumentException e){
            throw new ResourceNotFoundException(Constant.ERROR_CODE.UNAUTHORIZED_INTERNAL_ERROR, userId);
        }
    }

    @Override
    public UserResponse findById(String id) {
        try {
            String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
            if (!id.equals(currentUserId)){
                throw new AccessDeniedException(Constant.ERROR_CODE.ACCESS_DENIED_MSG, "user", id);
            }
            return identityClient.getUserResponseById(id);
        } catch(IllegalArgumentException e){
            throw new ResourceNotFoundException(Constant.ERROR_CODE.UNAUTHORIZED_ID_NOT_EXIST, id);
        }
    }

    @Override
    public void updateCustomer(CustomerUpdateRequest customerUpdateRequest) {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            UserRepresentation userRepresentation =
                    identityClient.getUserRepresentationById(id);
            userRepresentation.setFirstName(customerUpdateRequest.firstName());
            userRepresentation.setLastName(customerUpdateRequest.lastName());
            userRepresentation.setUsername(customerUpdateRequest.username());
            identityClient.updateUserResource(userRepresentation, id);

        } catch (IllegalArgumentException e){
            throw new ResourceNotFoundException(Constant.ERROR_CODE.UNAUTHORIZED_ID_NOT_EXIST, id);
        }
    }

}
