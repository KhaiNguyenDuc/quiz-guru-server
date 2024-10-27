package com.quizguru.quizzes.service.impl;

import com.quizguru.quizzes.client.library.LibraryClient;
import com.quizguru.quizzes.client.record.RecordClient;
import com.quizguru.quizzes.dto.request.*;
import com.quizguru.quizzes.dto.response.WordSetResponse;
import com.quizguru.quizzes.dto.request.vocabulary.ListToVocabRequest;
import com.quizguru.quizzes.dto.request.vocabulary.TextVocabRequest;
import com.quizguru.quizzes.dto.response.*;
import com.quizguru.quizzes.exception.InvalidRequestException;
import com.quizguru.quizzes.model.Question;
import com.quizguru.quizzes.producer.GenerateProducer;
import com.quizguru.quizzes.dto.request.text.BaseRequest;
import com.quizguru.quizzes.dto.request.text.HandledFileRequest;
import com.quizguru.quizzes.dto.request.text.RawFileRequest;
import com.quizguru.quizzes.dto.request.vocabulary.HandledFileVocabRequest;
import com.quizguru.quizzes.dto.request.vocabulary.RawFileVocabRequest;
import com.quizguru.quizzes.exception.AccessDeniedException;
import com.quizguru.quizzes.exception.ResourceNotFoundException;
import com.quizguru.quizzes.mapper.QuizMapper;
import com.quizguru.quizzes.model.Quiz;
import com.quizguru.quizzes.repository.QuestionRepository;
import com.quizguru.quizzes.repository.QuizRepository;
import com.quizguru.quizzes.service.QuizService;
import com.quizguru.quizzes.utils.AuthProvider;
import com.quizguru.quizzes.utils.Constant;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizServiceImpl implements QuizService {

    private final GenerateProducer generateProducer;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final LibraryClient libraryClient;
    private final RecordClient recordClient;

    @Override
    public GenerateQuizResponse createQuizByText(BaseRequest baseRequest) {
        String userId = AuthProvider.getUserId();
        Quiz quiz = QuizMapper.toQuiz(baseRequest);
        quiz.setUserId(userId);
        Quiz quizSaved = quizRepository.save(quiz);
        generateProducer.publishGenerateRequest(baseRequest.withId(userId, quizSaved.getId()));
        return QuizMapper.quizToGenerateQuizResponse(quizSaved);
    }

    @Override
    public GenerateQuizResponse createQuizByDocFile(RawFileRequest rawFileRequest) {
        String userId = AuthProvider.getUserId();
        Quiz quiz = QuizMapper.toQuiz(rawFileRequest);
        quiz.setUserId(userId);
        Quiz quizSaved = quizRepository.save(quiz);
        HandledFileRequest docFileVocabRequest = QuizMapper.toDocFileRequest(rawFileRequest, quizSaved.getId());
        generateProducer.publishDocFileVocabRequest(docFileVocabRequest);
        return QuizMapper.quizToGenerateQuizResponse(quizSaved);
    }

    @Override
    public GenerateQuizResponse createQuizByPdfFile(RawFileRequest rawFileRequest) {
        Quiz quiz = QuizMapper.toQuiz(rawFileRequest);
        quiz.setUserId(AuthProvider.getUserId());
        Quiz quizSaved = quizRepository.save(quiz);
        HandledFileRequest docFileVocabRequest = QuizMapper.toPdfFileRequest(rawFileRequest, quizSaved.getId());
        generateProducer.publishDocFileVocabRequest(docFileVocabRequest);
        return QuizMapper.quizToGenerateQuizResponse(quizSaved);
    }

    @Override
    public GenerateQuizResponse createQuizByTxtFile(RawFileRequest rawFileRequest) {
        Quiz quiz = QuizMapper.toQuiz(rawFileRequest);
        quiz.setUserId(AuthProvider.getUserId());
        Quiz quizSaved = quizRepository.save(quiz);
        HandledFileRequest docFileVocabRequest = QuizMapper.toTxtFileRequest(rawFileRequest, quizSaved.getId());
        generateProducer.publishDocFileVocabRequest(docFileVocabRequest);
        return QuizMapper.quizToGenerateQuizResponse(quizSaved);

    }

    @Override
    public void updateQuiz(QuizGenerateResult quizGenerateResult) {
        QuizRequest quizRequest = quizGenerateResult.quizRequest();
        Quiz quiz = quizRepository.findById(quizGenerateResult.quizId())
                        .orElseThrow(() -> new ResourceNotFoundException(Constant.ERROR_CODE.RESOURCE_NOT_FOUND,  "Quiz", quizGenerateResult.quizId()));

        List<Question> questions = QuizMapper.toQuestions(quizRequest.questions(), quiz);
        for(Question question: questions){
            for (Integer answer: question.getAnswers()){
                question.setAnswer(answer, question.getChoices());
            }
        }

        quiz.setQuestions(questions);
        quizRepository.save(quiz);
    }

    @Override
    public GenerateQuizResponse createVocabularyQuizByText(TextVocabRequest textVocabRequest) {
        String userId = AuthProvider.getUserId();
        Quiz quiz = QuizMapper.toQuiz(textVocabRequest);
        quiz.setUserId(AuthProvider.getUserId());
        Quiz quizSaved = quizRepository.save(quiz);
        generateProducer.publishTextVocabRequest(textVocabRequest.withId(userId, quizSaved.getId()));
        return QuizMapper.quizToGenerateQuizResponse(quizSaved);
    }

    @Override
    public DetailQuizResponse findDetailQuizById(String quizId) {
        String userId = AuthProvider.getUserId();
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.ERROR_CODE.RESOURCE_NOT_FOUND, "Quiz", quizId));
        if(!userId.equals(quiz.getUserId())){
            throw new AccessDeniedException(Constant.ERROR_CODE.ACCESS_DENIED, "Quiz", quizId);
        }
        try{
            ApiResponse<WordSetResponse> apiResponse = libraryClient.findWordSetByQuizId(quizId).getBody();
            WordSetResponse wordSetResponse;
            if(Objects.nonNull(apiResponse)){
                wordSetResponse = apiResponse.data();
                return QuizMapper.toDetailQuizResponse(quiz, wordSetResponse);
            }else{
                throw new InvalidRequestException(Constant.ERROR_CODE.INVALID_REQUEST_MSG, "Library service down");
            }

        }catch (ResponseStatusException ex){
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new ResourceNotFoundException(Constant.ERROR_CODE.RESOURCE_NOT_FOUND,  "Quiz", quizId);
            }
            throw new InvalidRequestException(Constant.ERROR_CODE.INVALID_REQUEST_MSG, ex.getMessage());
        }
    }

    @Override
    public ProvRecordResponse findProvisionDataForRecordById(String quizId, RecordRequest recordRequest) {

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.ERROR_CODE.RESOURCE_NOT_FOUND, "Quiz", quizId));

        try{
            List<RecordItemResponse> recordItemResponses = new ArrayList<>();
            List<RecordItemRequest> recordItemRequests = recordRequest.recordItems();
            for(RecordItemRequest recordItemRequest: recordItemRequests){
                Question question = questionRepository.findById(recordItemRequest.questionId())
                        .orElseThrow(() -> new ResourceNotFoundException(Constant.ERROR_CODE.RESOURCE_NOT_FOUND, "Question", recordItemRequest.questionId()));

                QuestionResponse questionResponse = QuizMapper.toQuestionResponse(question);
                List<ChoiceResponse> selectedChoiceResponses = questionResponse.choices().stream().filter(
                        choiceResponse -> recordItemRequest.selectedChoiceIds().contains(choiceResponse.id())
                ).toList();
                RecordItemResponse recordItemResponse = RecordItemResponse.builder()
                        .question(questionResponse)
                        .selectedChoices(selectedChoiceResponses)
                        .build();
                recordItemResponses.add(recordItemResponse);
            }
            return ProvRecordResponse.builder()
                    .recordItemResponses(recordItemResponses)
                    .duration(quiz.getDuration())
                    .givenText(quiz.getGivenText())
                    .build();

        }catch (Exception ex){
            throw new InvalidRequestException(Constant.ERROR_CODE.INVALID_REQUEST_MSG, ex.getMessage());
        }

    }

    @Override
    public void submitRecord(RecordRequest recordRequest) {
        String userId = AuthProvider.getUserId();
        UpdateRecordRequest updateRecordRequest = UpdateRecordRequest.builder()
                .quizId(recordRequest.quizId())
                .recordId(recordRequest.recordId())
                .recordItems(recordRequest.recordItems())
                .timeLeft(recordRequest.timeLeft())
                .userId(userId)
                .build();
        recordClient.updateRecord(updateRecordRequest);
    }

    @Override
    public GenerateQuizResponse createVocabularyQuizByList(ListToVocabRequest listVocabRequest) {
        String userId = AuthProvider.getUserId();
        Quiz quiz = QuizMapper.toQuiz(listVocabRequest);
        quiz.setUserId(AuthProvider.getUserId());
        Quiz quizSaved = quizRepository.save(quiz);
        generateProducer.publishListVocabRequest(listVocabRequest.withId(userId, quizSaved.getId()));
        return QuizMapper.quizToGenerateQuizResponse(quizSaved);
    }

    @Override
    public GenerateQuizResponse createVocabularyQuizByDocFile(RawFileVocabRequest rawFileVocabRequest){
        String userId = AuthProvider.getUserId();
        Quiz quiz = QuizMapper.toQuiz(rawFileVocabRequest);
        quiz.setUserId(userId);
        Quiz quizSaved = quizRepository.save(quiz);
        HandledFileVocabRequest docFileVocabRequest = QuizMapper.toDocFileVocabRequest(rawFileVocabRequest, quizSaved.getId());
        generateProducer.publishDocFileVocabRequest(docFileVocabRequest);
        return QuizMapper.quizToGenerateQuizResponse(quizSaved);
    }

    @Override
    public GenerateQuizResponse createVocabularyQuizByPDFFile(RawFileVocabRequest rawFileVocabRequest) {
        String userId = AuthProvider.getUserId();
        Quiz quiz = QuizMapper.toQuiz(rawFileVocabRequest);
        quiz.setUserId(userId);
        Quiz quizSaved = quizRepository.save(quiz);
        HandledFileVocabRequest pdfFileVocabRequest = QuizMapper.toPdfFileVocabRequest(rawFileVocabRequest, quizSaved.getId());
        generateProducer.publishDocFileVocabRequest(pdfFileVocabRequest);
        return QuizMapper.quizToGenerateQuizResponse(quizSaved);
    }

    @Override
    public GenerateQuizResponse createVocabularyQuizByTxtFile(RawFileVocabRequest rawFileVocabRequest) {
        String userId = AuthProvider.getUserId();
        Quiz quiz = QuizMapper.toQuiz(rawFileVocabRequest);
        quiz.setUserId(userId);
        Quiz quizSaved = quizRepository.save(quiz);
        HandledFileVocabRequest pdfFileVocabRequest = QuizMapper.toTxtFileVocabRequest(rawFileVocabRequest, quizSaved.getId());
        generateProducer.publishDocFileVocabRequest(pdfFileVocabRequest);
        return QuizMapper.quizToGenerateQuizResponse(quizSaved);
    }

    @Override
    public PageResponse<List<QuizResponse>> findAllQuizByUserId(String userId, Pageable pageable) {

        if (!AuthProvider.getUserId().equals(userId)){
            throw new AccessDeniedException(Constant.ERROR_CODE.ACCESS_DENIED, "user", userId);
        }
        Page<Quiz> quizzes = quizRepository.findAllByUserId(userId, pageable);

        List<QuizResponse> quizResponses = QuizMapper.toQuizResponses(quizzes.getContent());

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
                .orElseThrow(() -> new ResourceNotFoundException(Constant.ERROR_CODE.RESOURCE_NOT_FOUND, "Quiz", quizId));
        if(!userId.equals(quiz.getUserId())){
            throw new AccessDeniedException(Constant.ERROR_CODE.ACCESS_DENIED, "Quiz", quizId);
        }
        return QuizMapper.toQuizResponse(quiz);
    }

    @Override
    @Transactional
    public void deleteById(String quizId) {

        Optional<Quiz> quizOpt = quizRepository.findById(quizId);
        if(quizOpt.isEmpty()){
            throw new ResourceNotFoundException(Constant.ERROR_CODE.RESOURCE_NOT_FOUND, "quiz", quizId);
        }
        Quiz quiz = quizOpt.get();
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!userId.equals(quiz.getUserId())){
            throw new AccessDeniedException(Constant.ERROR_CODE.ACCESS_DENIED, "quiz", quizId);
        }
        quiz.setIsDeleted(true);
        quizRepository.save(quiz);
        libraryClient.removeQuiz(quiz.getId());
    }

}
