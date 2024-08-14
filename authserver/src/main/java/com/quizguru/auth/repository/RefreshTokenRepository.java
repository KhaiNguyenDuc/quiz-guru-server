package com.quizguru.auth.repository;

import com.quizguru.auth.model.RefreshToken;
import com.quizguru.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findByUser(User user);

    Boolean existsByToken(String token);

    Optional<RefreshToken> findByToken(String refreshToken);
}
