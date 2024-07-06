package com.quizguru.auth.repository;

import com.quizguru.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Boolean existsByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
}
