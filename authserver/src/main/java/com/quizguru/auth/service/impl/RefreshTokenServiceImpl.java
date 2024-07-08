package com.quizguru.auth.service.impl;

import com.quizguru.auth.config.RefreshTokenConfig;
import com.quizguru.auth.exception.ResourceNotFoundException;
import com.quizguru.auth.model.RefreshToken;
import com.quizguru.auth.model.User;
import com.quizguru.auth.repository.RefreshTokenRepository;
import com.quizguru.auth.repository.UserRepository;
import com.quizguru.auth.service.RefreshTokenService;
import com.quizguru.auth.utils.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final RefreshTokenConfig refreshTokenConfig;

    @Override
    public RefreshToken generateRefreshToken(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.ERROR_CODE.UNAUTHORIZED_ID_NOT_EXIST, userId));

        Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findByUser(user);
        if (refreshTokenOpt.isPresent()) {
            return renewRefreshToken(refreshTokenOpt.get());
        }

        return createRefreshToken(user);
    }

    private RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenConfig.getExpirationMs()))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    private RefreshToken renewRefreshToken(RefreshToken refreshToken) {
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenConfig.getExpirationMs()));
        refreshToken.setToken(UUID.randomUUID().toString());
        return refreshTokenRepository.save(refreshToken);
    }
}
