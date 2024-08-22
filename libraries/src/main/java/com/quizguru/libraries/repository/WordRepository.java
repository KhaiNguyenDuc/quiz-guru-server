package com.quizguru.libraries.repository;

import com.quizguru.libraries.model.Word;
import com.quizguru.libraries.model.WordSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordRepository extends JpaRepository<Word, String> {
    Page<Word> findAllByWordSet(WordSet wordSet, Pageable pageable);

    Optional<Word> findByNameAndWordSet(String word, WordSet wordSet);

    boolean existsByNameAndWordSet(String name, WordSet wordSet);

    List<Word> findByWordSet(WordSet wordSet);
}
