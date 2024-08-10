package com.quizguru.quizzes.repository;

import com.quizguru.quizzes.model.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface QuizRepository extends JpaRepository<Quiz, String> {
    @Query("SELECT q FROM Quiz q WHERE q.isDeleted = false AND q.userId = :userId")
    Page<Quiz> findAllByUserId(@Param("userId") String userId, Pageable pageable);

    boolean existsByUserId(String userId);
}
