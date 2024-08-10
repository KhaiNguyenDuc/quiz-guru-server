package com.quizguru.libraries.repository;

import com.quizguru.libraries.model.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibraryRepository extends JpaRepository<Library, String> {
    Optional<Library> findByUserId(String userId);
}
