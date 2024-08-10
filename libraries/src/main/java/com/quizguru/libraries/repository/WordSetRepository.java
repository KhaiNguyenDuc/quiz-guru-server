package com.quizguru.libraries.repository;

import com.quizguru.libraries.model.Library;
import com.quizguru.libraries.model.WordSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WordSetRepository extends JpaRepository<WordSet, String> {

    Optional<WordSet> findByNameAndLibrary(String name, Library library);
}
