package com.quizguru.contexts.repository;

import com.quizguru.contexts.model.Context;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContextRepository extends JpaRepository<Context, String> {

}
