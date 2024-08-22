package com.quizguru.quizzes.repository;

import com.quizguru.quizzes.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {
}
