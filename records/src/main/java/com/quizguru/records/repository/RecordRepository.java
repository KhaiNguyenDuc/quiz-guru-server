package com.quizguru.records.repository;

import com.quizguru.records.model.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, String> {
    Page<Record> findByUserId(String userId, Pageable pageable);

    boolean existsByUserId(String userId);
}
