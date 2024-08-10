package com.quizguru.libraries.service;

import com.quizguru.libraries.dto.request.WordRequest;
import com.quizguru.libraries.dto.request.WordSetRequest;
import com.quizguru.libraries.dto.response.LibraryResponse;
import com.quizguru.libraries.dto.response.PageResponse;
import com.quizguru.libraries.dto.response.WordResponse;
import com.quizguru.libraries.dto.response.WordSetResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

public interface LibraryService {
    String createWordSet(WordSetRequest wordSetRequest);

    WordSetResponse updateWordSet(WordSetRequest wordSetRequest, String wordSetId);

    PageResponse<WordSetResponse> findWordsById(String wordSetId,  Pageable pageable);

    void deleteById(String wordSetId);

    void bindQuiz(String wordSetId, String quizId);

    List<WordResponse> addDefinition(String wordSetId, List<String> words);

    WordResponse updateWordDefinition(String wordId, WordRequest wordRequest);
}
