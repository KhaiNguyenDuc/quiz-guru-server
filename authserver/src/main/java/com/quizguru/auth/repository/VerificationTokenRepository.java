package com.quizguru.auth.repository;

import com.quizguru.auth.model.User;
import com.quizguru.auth.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, String> {
    Optional<VerificationToken> findByUser(User user);
}
