package com.quizguru.libraries.repository;

import com.quizguru.libraries.model.Library;
import com.quizguru.libraries.model.Word;
import com.quizguru.libraries.model.WordSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WordSetRepository extends JpaRepository<WordSet, String> {

    @Query("SELECT ws FROM WordSet ws WHERE ws.isDeleted = false AND ws.library = :library AND ws.name = :name")
    Optional<WordSet> findByNameAndLibrary(String name, Library library);


    @Query("SELECT ws FROM WordSet ws WHERE ws.isDeleted = false AND ws.library = :library")
    Page<WordSet> findAllByLibrary(Library library, Pageable pageable);

    Optional<WordSet> findByQuizId(String quizId);

    @Query("SELECT ws FROM WordSet ws WHERE ws.isDeleted = false AND ws.quizId = :quizId")
    Optional<WordSet> findByQuizIdIsDeletedFalse(String quizId);
}
