package com.quizguru.auth.repository;

import com.quizguru.auth.model.PasswordResetToken;
import com.quizguru.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, String> {
    Optional<PasswordResetToken> findByUser(User user);
}
