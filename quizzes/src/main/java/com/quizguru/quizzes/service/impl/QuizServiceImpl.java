package com.quizguru.quizzes.service.impl;

import com.quizguru.quizzes.amqp.properties.AmqpProducer;
import com.quizguru.quizzes.dto.request.*;
import com.quizguru.quizzes.dto.response.GenerateQuizResponse;
import com.quizguru.quizzes.dto.response.PageResponse;
import com.quizguru.quizzes.dto.response.QuizResponse;
import com.quizguru.quizzes.exception.AccessDeniedException;
import com.quizguru.quizzes.exception.ResourceNotFoundException;
import com.quizguru.quizzes.mapper.QuizMapper;
import com.quizguru.quizzes.model.Quiz;
import com.quizguru.quizzes.repository.QuizRepository;
import com.quizguru.quizzes.service.QuizService;
import com.quizguru.quizzes.utils.AuthProvider;
import com.quizguru.quizzes.utils.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.lang.module.ResolutionException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizServiceImpl implements QuizService {

    private final AmqpProducer amqpProducer;
    private final QuizRepository quizRepository;

    @Override
    public GenerateQuizResponse createQuizByText(GenerateRequest generateRequest) {
        Quiz quiz = QuizMapper.generateRequestToQuiz(generateRequest);
        quiz.setUserId(AuthProvider.getUserId());
        Quiz quizSaved = quizRepository.save(quiz);
        amqpProducer.publishGenerateRequest(generateRequest);
        return QuizMapper.quizToGenerateQuizResponse(quizSaved);
    }

    @Override
    public GenerateQuizResponse createVocabularyQuizByText(TextVocabRequest textVocabRequest) {
        Quiz quiz = QuizMapper.textVocabRequestToQuiz(textVocabRequest);
        quiz.setUserId(AuthProvider.getUserId());
        Quiz quizSaved = quizRepository.save(quiz);
        amqpProducer.publishTextVocabRequest(textVocabRequest);
        return QuizMapper.quizToGenerateQuizResponse(quizSaved);
    }

    @Override
    public GenerateQuizResponse createVocabularyQuizByDocFile(RawFileVocabRequest rawFileVocabRequest){
        Quiz quiz = QuizMapper.docFileVocabRequestToQuiz(rawFileVocabRequest);
        quiz.setUserId(AuthProvider.getUserId());
        Quiz quizSaved = quizRepository.save(quiz);
        HandledFileVocabRequest docFileVocabRequest = QuizMapper.fileVocabToDocFileVocabRequest(rawFileVocabRequest);
        amqpProducer.publishDocFileVocabRequest(docFileVocabRequest);
        return QuizMapper.quizToGenerateQuizResponse(quizSaved);
    }

    @Override
    public GenerateQuizResponse createVocabularyQuizByPDFFile(RawFileVocabRequest rawFileVocabRequest) {
        Quiz quiz = QuizMapper.docFileVocabRequestToQuiz(rawFileVocabRequest);
        quiz.setUserId(AuthProvider.getUserId());
        Quiz quizSaved = quizRepository.save(quiz);
        HandledFileVocabRequest pdfFileVocabRequest = QuizMapper.fileVocabToPdfFileVocabRequest(rawFileVocabRequest);
        amqpProducer.publishDocFileVocabRequest(pdfFileVocabRequest);
        return QuizMapper.quizToGenerateQuizResponse(quizSaved);
    }

    @Override
    public GenerateQuizResponse createVocabularyQuizByTxtFile(RawFileVocabRequest rawFileVocabRequest) {
        Quiz quiz = QuizMapper.docFileVocabRequestToQuiz(rawFileVocabRequest);
        quiz.setUserId(AuthProvider.getUserId());
        Quiz quizSaved = quizRepository.save(quiz);
        HandledFileVocabRequest pdfFileVocabRequest = QuizMapper.fileVocabToTxtFileVocabRequest(rawFileVocabRequest);
        amqpProducer.publishDocFileVocabRequest(pdfFileVocabRequest);
        return QuizMapper.quizToGenerateQuizResponse(quizSaved);
    }

    @Override
    public PageResponse<List<QuizResponse>> findAllQuizByCurrentUser(Pageable pageable) {
        String userId = AuthProvider.getUserId();
        Page<Quiz> quizzes = quizRepository.findAllByUserId(userId, pageable);

        List<QuizResponse> quizResponses = QuizMapper.quizzesToQuizResponses(quizzes.getContent());

        return new PageResponse<>(
                quizResponses,
                pageable.getPageNumber(),
                pageable.getPageSize(),
                quizzes.getTotalPages(),
                quizzes.getNumberOfElements()
        );
    }

    @Override
    public QuizResponse findQuizById(String quizId) {
        String userId = AuthProvider.getUserId();
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.RESOURCE_NOT_FOUND, "Quiz", quizId));
        if(!userId.equals(quiz.getUserId())){
            throw new AccessDeniedException(Constant.ACCESS_DENIED, "Quiz", quizId);
        }
        return QuizMapper.quizToQuizResponse(quiz);
    }
}
