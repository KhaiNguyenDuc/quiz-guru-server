package com.quizguru.quizzes.service;

import com.quizguru.quizzes.dto.request.QuizGenerateResult;
import com.quizguru.quizzes.dto.request.text.RawFileRequest;
import com.quizguru.quizzes.dto.request.vocabulary.ListToVocabRequest;
import com.quizguru.quizzes.dto.request.vocabulary.RawFileVocabRequest;
import com.quizguru.quizzes.dto.request.text.BaseRequest;
import com.quizguru.quizzes.dto.request.vocabulary.TextVocabRequest;
import com.quizguru.quizzes.dto.response.DetailQuizResponse;
import com.quizguru.quizzes.dto.response.GenerateQuizResponse;
import com.quizguru.quizzes.dto.response.PageResponse;
import com.quizguru.quizzes.dto.response.QuizResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuizService {
    GenerateQuizResponse createQuizByText(BaseRequest baseRequest);

    GenerateQuizResponse createVocabularyQuizByList(ListToVocabRequest listVocabRequest);

    GenerateQuizResponse createVocabularyQuizByDocFile(RawFileVocabRequest rawFileVocabRequest);

    GenerateQuizResponse createVocabularyQuizByPDFFile(RawFileVocabRequest fileVocabRequest);
    GenerateQuizResponse createVocabularyQuizByTxtFile(RawFileVocabRequest fileVocabRequest);

    PageResponse<List<QuizResponse>> findAllQuizByUserId(String userId, Pageable pageable);

    QuizResponse findQuizById(String quizId);

    void deleteById(String quizId);

    GenerateQuizResponse createQuizByDocFile(RawFileRequest rawFileRequest);

    GenerateQuizResponse createQuizByPdfFile(RawFileRequest rawFileRequest);

    GenerateQuizResponse createQuizByTxtFile(RawFileRequest rawFileRequest);

    void updateQuiz(QuizGenerateResult quizGenerateResult);

    GenerateQuizResponse createVocabularyQuizByText(TextVocabRequest textVocabRequest);

    DetailQuizResponse findDetailQuizById(String quizId);
}
