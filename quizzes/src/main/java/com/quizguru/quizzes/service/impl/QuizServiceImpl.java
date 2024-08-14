package com.quizguru.quizzes.service.impl;

import com.quizguru.quizzes.producer.GenerateProducer;
import com.quizguru.quizzes.dto.request.text.BaseRequest;
import com.quizguru.quizzes.dto.request.text.HandledFileRequest;
import com.quizguru.quizzes.dto.request.text.RawFileRequest;
import com.quizguru.quizzes.dto.request.vocabulary.HandledFileVocabRequest;
import com.quizguru.quizzes.dto.request.vocabulary.RawFileVocabRequest;
import com.quizguru.quizzes.dto.request.vocabulary.TextVocabRequest;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizServiceImpl implements QuizService {

    private final GenerateProducer generateProducer;
    private final QuizRepository quizRepository;

    @Override
    public GenerateQuizResponse createQuizByText(BaseRequest baseRequest) {
        Quiz quiz = QuizMapper.generateRequestToQuiz(baseRequest);
        quiz.setUserId(AuthProvider.getUserId());
        Quiz quizSaved = quizRepository.save(quiz);
        generateProducer.publishGenerateRequest(baseRequest);
        return QuizMapper.quizToGenerateQuizResponse(quizSaved);
    }

    @Override
    public GenerateQuizResponse createQuizByDocFile(RawFileRequest rawFileRequest) {
        Quiz quiz = QuizMapper.fileRequestToQuiz(rawFileRequest);
        quiz.setUserId(AuthProvider.getUserId());
        Quiz quizSaved = quizRepository.save(quiz);
        HandledFileRequest docFileVocabRequest = QuizMapper.fileToDocFileRequest(rawFileRequest);
        generateProducer.publishDocFileVocabRequest(docFileVocabRequest);
        return QuizMapper.quizToGenerateQuizResponse(quizSaved);
    }

    @Override
    public GenerateQuizResponse createQuizByPdfFile(RawFileRequest rawFileRequest) {
        Quiz quiz = QuizMapper.fileRequestToQuiz(rawFileRequest);
        quiz.setUserId(AuthProvider.getUserId());
        Quiz quizSaved = quizRepository.save(quiz);
        HandledFileRequest docFileVocabRequest = QuizMapper.fileToPdfFileRequest(rawFileRequest);
        generateProducer.publishDocFileVocabRequest(docFileVocabRequest);
        return QuizMapper.quizToGenerateQuizResponse(quizSaved);
    }

    @Override
    public GenerateQuizResponse createQuizByTxtFile(RawFileRequest rawFileRequest) {
        Quiz quiz = QuizMapper.fileRequestToQuiz(rawFileRequest);
        quiz.setUserId(AuthProvider.getUserId());
        Quiz quizSaved = quizRepository.save(quiz);
        HandledFileRequest docFileVocabRequest = QuizMapper.fileToTxtFileRequest(rawFileRequest);
        generateProducer.publishDocFileVocabRequest(docFileVocabRequest);
        return QuizMapper.quizToGenerateQuizResponse(quizSaved);

    }

    @Override
    public GenerateQuizResponse createVocabularyQuizByText(TextVocabRequest textVocabRequest) {
        Quiz quiz = QuizMapper.textVocabRequestToQuiz(textVocabRequest);
        quiz.setUserId(AuthProvider.getUserId());
        Quiz quizSaved = quizRepository.save(quiz);
        generateProducer.publishTextVocabRequest(textVocabRequest);
        return QuizMapper.quizToGenerateQuizResponse(quizSaved);
    }

    @Override
    public GenerateQuizResponse createVocabularyQuizByDocFile(RawFileVocabRequest rawFileVocabRequest){
        Quiz quiz = QuizMapper.fileVocabRequestToQuiz(rawFileVocabRequest);
        quiz.setUserId(AuthProvider.getUserId());
        Quiz quizSaved = quizRepository.save(quiz);
        HandledFileVocabRequest docFileVocabRequest = QuizMapper.fileVocabToDocFileVocabRequest(rawFileVocabRequest);
        generateProducer.publishDocFileVocabRequest(docFileVocabRequest);
        return QuizMapper.quizToGenerateQuizResponse(quizSaved);
    }

    @Override
    public GenerateQuizResponse createVocabularyQuizByPDFFile(RawFileVocabRequest rawFileVocabRequest) {
        Quiz quiz = QuizMapper.fileVocabRequestToQuiz(rawFileVocabRequest);
        quiz.setUserId(AuthProvider.getUserId());
        Quiz quizSaved = quizRepository.save(quiz);
        HandledFileVocabRequest pdfFileVocabRequest = QuizMapper.fileVocabToPdfFileVocabRequest(rawFileVocabRequest);
        generateProducer.publishDocFileVocabRequest(pdfFileVocabRequest);
        return QuizMapper.quizToGenerateQuizResponse(quizSaved);
    }

    @Override
    public GenerateQuizResponse createVocabularyQuizByTxtFile(RawFileVocabRequest rawFileVocabRequest) {
        Quiz quiz = QuizMapper.fileVocabRequestToQuiz(rawFileVocabRequest);
        quiz.setUserId(AuthProvider.getUserId());
        Quiz quizSaved = quizRepository.save(quiz);
        HandledFileVocabRequest pdfFileVocabRequest = QuizMapper.fileVocabToTxtFileVocabRequest(rawFileVocabRequest);
        generateProducer.publishDocFileVocabRequest(pdfFileVocabRequest);
        return QuizMapper.quizToGenerateQuizResponse(quizSaved);
    }

    @Override
    public PageResponse<List<QuizResponse>> findAllQuizByUserId(String userId, Pageable pageable) {
        if(!quizRepository.existsByUserId(userId)){
            throw new ResourceNotFoundException(Constant.RESOURCE_NOT_FOUND, "user", userId);
        }
        if (!AuthProvider.getUserId().equals(userId)){
            throw new AccessDeniedException(Constant.ACCESS_DENIED, "user", userId);
        }
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

    @Override
    public void deleteById(String quizId) {

        Optional<Quiz> quizOpt = quizRepository.findById(quizId);
        if(quizOpt.isEmpty()){
            throw new ResourceNotFoundException(Constant.RESOURCE_NOT_FOUND, "quiz", quizId);
        }
        Quiz quiz = quizOpt.get();
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!userId.equals(quiz.getUserId())){
            throw new AccessDeniedException(Constant.ACCESS_DENIED, "quiz", quizId);
        }
        quiz.setIsDeleted(true);
        quizRepository.save(quiz);
    }

}
