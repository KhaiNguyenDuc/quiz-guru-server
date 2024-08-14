package com.quizguru.auth.service.impl;

import com.quizguru.auth.dto.request.LoginCredentials;
import com.quizguru.auth.dto.request.RegisterCredentials;
import com.quizguru.auth.dto.response.RegisterResponse;
import com.quizguru.auth.dto.response.TokenResponse;
import com.quizguru.auth.dto.response.TokenValidationResponse;
import com.quizguru.auth.exception.ResourceExistException;
import com.quizguru.auth.exception.TokenValidationException;
import com.quizguru.auth.exception.UnauthorizedException;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(loginCredentials.username(), loginCredentials.password());
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

    @Override
    public RegisterResponse register(RegisterCredentials registerCredentials) {

        if(userRepository.existsByEmail(registerCredentials.email())){
            throw new ResourceExistException("Email already exist");
        }
        return createUser(registerCredentials);
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
                .build();

        User userSaved = userRepository.save(user);
        return UserMapper.userToRegisterResponse(userSaved);
    }
}
