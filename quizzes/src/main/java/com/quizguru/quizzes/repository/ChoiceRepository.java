package com.quizguru.quizzes.repository;

import com.quizguru.quizzes.model.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, String> {
}
