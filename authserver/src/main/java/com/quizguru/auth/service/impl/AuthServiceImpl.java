package com.quizguru.auth.service.impl;

import com.quizguru.auth.client.LibraryClient;
import com.quizguru.auth.dto.request.LoginCredentials;
import com.quizguru.auth.dto.request.RegisterCredentials;
import com.quizguru.auth.dto.response.*;
import com.quizguru.auth.exception.*;
import com.quizguru.auth.jwt.JwtTokenProvider;
import com.quizguru.auth.mapper.UserMapper;
import com.quizguru.auth.model.RefreshToken;
import com.quizguru.auth.model.Role;
import com.quizguru.auth.model.User;
import com.quizguru.auth.model.UserPrincipal;
import com.quizguru.auth.model.enums.RoleName;
import com.quizguru.auth.repository.RoleRepository;
import com.quizguru.auth.repository.UserRepository;
import com.quizguru.auth.service.AuthService;
import com.quizguru.auth.service.RefreshTokenService;
import com.quizguru.auth.utils.Constant;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final RefreshTokenService refreshTokenService;
    private final LibraryClient libraryClient;

    @Override
    public TokenValidationResponse validateJwtToken(String token) {
        boolean isTokenValid = tokenProvider.validateToken(token);
        log.error("Validate token:" + isTokenValid);
        if(!isTokenValid){
            throw new TokenValidationException(Constant.ERROR_CODE.INVALID_TOKEN);
        }
        String userId = tokenProvider.getUserIdFromJwt(token);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UnauthorizedException(Constant.ERROR_CODE.UNAUTHORIZED_ID_NOT_EXIST, userId));
        Set<String> userAuthorities = user.getRoles().stream()
                .map(r -> "ROLE_" + r.getName().toString())
                .collect(Collectors.toSet());

        return TokenValidationResponse.builder()
                .userId(userId)
                .userAuthorities(userAuthorities)
                .build();
    }

    @Override
    public TokenResponse authenticate(LoginCredentials loginCredentials) {

        try {
            UsernamePasswordAuthenticationToken token;
            if(Objects.nonNull(loginCredentials.username())){
                token =
                        new UsernamePasswordAuthenticationToken(loginCredentials.username(), loginCredentials.password());
            }else{
                token =
                        new UsernamePasswordAuthenticationToken(loginCredentials.email(), loginCredentials.password());
            }
             Authentication auth = authManager.authenticate(token);
            String jwt = tokenProvider.generateToken(auth);

            UserPrincipal userDetails = (UserPrincipal) auth.getPrincipal();
            RefreshToken refreshToken = refreshTokenService.generateOrRenewRefreshToken(userDetails.getId());

            return TokenResponse.builder()
                    .accessToken(jwt)
                    .refreshToken(refreshToken.getToken())
                    .build();
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException(Constant.ERROR_CODE.INVALID_CREDENTIALS);
        }
    }

    @Transactional
    @Override
    public RegisterResponse register(RegisterCredentials registerCredentials) {

        if(userRepository.existsByEmail(registerCredentials.email())){
            throw new ResourceExistException("Email already exist");
        }
        return createUser(registerCredentials);
    }

    @Override
    public UserResponse findById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.ERROR_CODE.UNAUTHORIZED_ID_NOT_EXIST, id));
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.getId().equals(userPrincipal.getId())){
            throw new AccessDeniedException(Constant.ERROR_CODE.ACCESS_DENIED_MSG, "user", id);
        }
        return UserMapper.toUserResponse(user);

    }

    @Override
    public String findRoleFromUserId(String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.ERROR_CODE.UNAUTHORIZED_ID_NOT_EXIST, userId));

        return user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.joining(", "));

    }

    private RegisterResponse createUser(RegisterCredentials registerCredentials){

        String encodedPassword = encoder.encode(registerCredentials.password());

        Role role = roleRepository.findByName(RoleName.USER);


        User user = User.builder()
                .email(registerCredentials.email())
                .username(registerCredentials.username())
                .password(encodedPassword)
                .roles(List.of(role))
                .isEnable(false)
                .libraryId("")
                .build();

        User userSaved = userRepository.save(user);
        String libraryId = createLibrary(userSaved.getId());
        user.setLibraryId(libraryId);
        return UserMapper.toRegisterResponse(userSaved);
    }

    private String createLibrary(String userId){
        try{
            ApiResponse apiResponse = libraryClient.create(userId).getBody();
            if(Objects.nonNull(apiResponse)){
                return apiResponse.data().toString();
            }
            return "";
        }catch (ResponseStatusException ex){
            throw new InvalidRequestException(Constant.ERROR_CODE.SOMETHING_WENT_WRONG, ex.getMessage());
        }

    }
}
