package com.quizguru.records.repository;

import com.quizguru.records.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, String> {
    List<Record> findByUserId(String userId);

    boolean existsByUserId(String userId);
}