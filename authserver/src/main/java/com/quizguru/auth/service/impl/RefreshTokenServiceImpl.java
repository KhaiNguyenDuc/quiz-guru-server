package com.quizguru.auth.service.impl;

import com.quizguru.auth.properties.RefreshTokenProperties;
import com.quizguru.auth.dto.request.RefreshTokenRequest;
import com.quizguru.auth.dto.response.TokenResponse;
import com.quizguru.auth.exception.InvalidRefreshTokenException;
import com.quizguru.auth.exception.ResourceNotFoundException;
import com.quizguru.auth.jwt.JwtTokenProvider;
import com.quizguru.auth.model.RefreshToken;
import com.quizguru.auth.model.User;
import com.quizguru.auth.repository.RefreshTokenRepository;
import com.quizguru.auth.repository.UserRepository;
import com.quizguru.auth.service.RefreshTokenService;
import com.quizguru.auth.utils.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final RefreshTokenProperties refreshTokenProperties;
    private final JwtTokenProvider tokenProvider;

    @Override
    public TokenResponse renewAccessToken(RefreshTokenRequest refreshTokenRequest) {

        String token = refreshTokenRequest.token();

        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
               .orElseThrow(() -> new InvalidRefreshTokenException(Constant.ERROR_CODE.INVALID_REFRESH_TOKEN, token));

        if (refreshToken.getExpiredDate().compareTo(Instant.now()) < 0) {
            throw new InvalidRefreshTokenException(Constant.ERROR_CODE.REFRESH_TOKEN_EXPIRED, token);
        }

        User user = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.ERROR_CODE.REFRESH_TOKEN_NOT_FOUND, token));

        String accessToken = tokenProvider.generateTokenFromUserId(user.getId(), user.getRoles());

        return TokenResponse.builder()
               .accessToken(accessToken)
               .refreshToken(refreshToken.getToken())
               .build();
    }

    @Override
    public RefreshToken generateOrRenewRefreshToken(String userId) {
        log.error(String.valueOf(refreshTokenProperties.getExpirationMs()));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.ERROR_CODE.UNAUTHORIZED_ID_NOT_EXIST, userId));

        Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findByUser(user);
        RefreshToken refreshToken;
        if (refreshTokenOpt.isEmpty()) {
            refreshToken = RefreshToken.builder()
                    .user(user)
                    .token(UUID.randomUUID().toString())
                    .expiredDate(Instant.now().plusMillis(refreshTokenProperties.getExpirationMs()))
                    .build();
        }else{
            refreshToken = refreshTokenOpt.get();
            refreshToken.setExpiredDate(Instant.now().plusMillis(refreshTokenProperties.getExpirationMs()));
            refreshToken.setToken(UUID.randomUUID().toString());
        }
        return refreshTokenRepository.save(refreshToken);

    }
}
