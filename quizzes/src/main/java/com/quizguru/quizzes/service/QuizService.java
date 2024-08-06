package com.quizguru.quizzes.service;

import com.quizguru.quizzes.dto.request.RawFileVocabRequest;
import com.quizguru.quizzes.dto.request.GenerateRequest;
import com.quizguru.quizzes.dto.request.TextVocabRequest;
import com.quizguru.quizzes.dto.response.GenerateQuizResponse;
import com.quizguru.quizzes.dto.response.PageResponse;
import com.quizguru.quizzes.dto.response.QuizResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuizService {
    GenerateQuizResponse createQuizByText(GenerateRequest generateRequest);

    GenerateQuizResponse createVocabularyQuizByText(TextVocabRequest textVocabRequest);

    GenerateQuizResponse createVocabularyQuizByDocFile(RawFileVocabRequest rawFileVocabRequest);

    GenerateQuizResponse createVocabularyQuizByPDFFile(RawFileVocabRequest fileVocabRequest);
    GenerateQuizResponse createVocabularyQuizByTxtFile(RawFileVocabRequest fileVocabRequest);

    PageResponse<List<QuizResponse>> findAllQuizByCurrentUser(Pageable pageable);

    QuizResponse findQuizById(String quizId);
}
