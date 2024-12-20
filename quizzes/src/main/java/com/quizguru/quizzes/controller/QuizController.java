package com.quizguru.quizzes.controller;

import com.quizguru.quizzes.dto.request.QuizGenerateResult;
import com.quizguru.quizzes.dto.request.RecordItemRequest;
import com.quizguru.quizzes.dto.request.RecordRequest;
import com.quizguru.quizzes.dto.request.text.RawFileRequest;
import com.quizguru.quizzes.dto.request.vocabulary.ListToVocabRequest;
import com.quizguru.quizzes.dto.request.vocabulary.RawFileVocabRequest;
import com.quizguru.quizzes.dto.request.text.BaseRequest;
import com.quizguru.quizzes.dto.request.vocabulary.TextVocabRequest;
import com.quizguru.quizzes.dto.response.*;
import com.quizguru.quizzes.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/quizzes")
@RequiredArgsConstructor
@Slf4j
public class QuizController {

    private final QuizService quizService;

    @MessageMapping("/submit")
    @SendTo("/topic/submit")
    public ResponseEntity<ApiResponse<SubmitRecordResponse>> submitRecord(
            @Payload RecordRequest recordRequest,
            SimpMessageHeaderAccessor headerAccessor
    ){
        String userId = (String) headerAccessor.getSessionAttributes().get("user"); // Retrieve the user ID
        log.error("Current User ID: " + userId);
        SubmitRecordResponse submitRecordResponse = quizService.submitRecord(recordRequest, userId);
        return new ResponseEntity<>(new ApiResponse<>(submitRecordResponse, "success"), HttpStatus.OK);
    }

    @PostMapping("/text")
    public ResponseEntity<ApiResponse<GenerateQuizResponse>> createQuizByText(
            @RequestBody BaseRequest baseRequest
    ){
        GenerateQuizResponse response = quizService.createQuizByText(baseRequest);
        return new ResponseEntity<>(new ApiResponse<>(response, "success"), HttpStatus.OK);
    }

    @PostMapping(value = "/doc", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ApiResponse<GenerateQuizResponse>> createQuizByDocFile(
            @ModelAttribute RawFileRequest rawFileRequest
    ){
        GenerateQuizResponse response = quizService.createQuizByDocFile(rawFileRequest);
        return new ResponseEntity<>(new ApiResponse<>(response, "success"), HttpStatus.OK);
    }

    @PostMapping(value = "/pdf", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ApiResponse<GenerateQuizResponse>> createQuizByPdfFile(
            @ModelAttribute RawFileRequest rawFileRequest
    ){
        GenerateQuizResponse response = quizService.createQuizByPdfFile(rawFileRequest);
        return new ResponseEntity<>(new ApiResponse<>(response, "success"), HttpStatus.OK);
    }

    @PostMapping(value = "/txt", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ApiResponse<GenerateQuizResponse>> createQuizByTxtFile(
            @ModelAttribute RawFileRequest rawFileRequest
    ){
        GenerateQuizResponse response = quizService.createQuizByTxtFile(rawFileRequest);
        return new ResponseEntity<>(new ApiResponse<>(response, "success"), HttpStatus.OK);
    }

    @PostMapping("/vocabulary/list")
    public ResponseEntity<ApiResponse<GenerateQuizResponse>> createVocabularyQuizByList(
            @RequestBody ListToVocabRequest listToVocabRequest
    ){
        GenerateQuizResponse response = quizService.createVocabularyQuizByList(listToVocabRequest);
        return new ResponseEntity<>(new ApiResponse<>(response, "success"), HttpStatus.OK);
    }

    @PostMapping("/vocabulary/text")
    public ResponseEntity<ApiResponse<GenerateQuizResponse>> createVocabularyQuizByText(
            @RequestBody TextVocabRequest textVocabRequest
    ){
        GenerateQuizResponse response = quizService.createVocabularyQuizByText(textVocabRequest);
        return new ResponseEntity<>(new ApiResponse<>(response, "success"), HttpStatus.OK);
    }

    @PostMapping(value = "/vocabulary/doc", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE } )
    public ResponseEntity<ApiResponse<GenerateQuizResponse>> createVocabularyQuizByDocFile(
            @ModelAttribute RawFileVocabRequest rawFileVocabRequest
    ){
        GenerateQuizResponse response = quizService.createVocabularyQuizByDocFile(rawFileVocabRequest);
        return new ResponseEntity<>(new ApiResponse<>(response, "success"), HttpStatus.OK);
    }

    @PostMapping(value = "/vocabulary/pdf", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE } )
    public ResponseEntity<ApiResponse<GenerateQuizResponse>> createVocabularyQuizByPdfFile(
            @ModelAttribute RawFileVocabRequest rawFileVocabRequest
    ){
        GenerateQuizResponse response = quizService.createVocabularyQuizByPDFFile(rawFileVocabRequest);
        return new ResponseEntity<>(new ApiResponse<>(response, "success"), HttpStatus.OK);
    }

    @PostMapping(value = "/vocabulary/txt", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE } )
    public ResponseEntity<ApiResponse<GenerateQuizResponse>> createVocabularyQuizByTxt(
            @ModelAttribute RawFileVocabRequest rawFileVocabRequest
    ){
        GenerateQuizResponse response = quizService.createVocabularyQuizByTxtFile(rawFileVocabRequest);
        return new ResponseEntity<>(new ApiResponse<>(response, "success"), HttpStatus.OK);
    }

    @GetMapping(value = "/users/current")
    public ResponseEntity<PageResponse<List<QuizResponse>>> findAllQuizByCurrentUser(
            @RequestParam(name = "page", defaultValue ="0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size
    ){
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        PageResponse<List<QuizResponse>> response = quizService.findAllQuizByUserId(id, PageRequest.of(page, size));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<QuizResponse>> findQuizById(@RequestParam("id") String quizId) {
        QuizResponse response = quizService.findQuizById(quizId);
        return new ResponseEntity<>(new ApiResponse<>(response, "success"), HttpStatus.OK);
    }

    @GetMapping("/detail")
    public ResponseEntity<ApiResponse<DetailQuizResponse>> findDetailQuizById(@RequestParam("id") String quizId) {
        log.debug("Start findDetailQuizById");
        DetailQuizResponse response = quizService.findDetailQuizById(quizId);
        log.debug("End findDetailQuizById");
        return new ResponseEntity<>(new ApiResponse<>(response, "success"), HttpStatus.OK);
    }

    @PostMapping("/internal/prov/record")
    ResponseEntity<ApiResponse<ProvRecordResponse>> findProvisionDataForRecordById(
            @RequestParam("id") String quizId,
            @RequestBody List<RecordItemRequest> recordItemRequests
    ) {
        ProvRecordResponse responses = quizService.findProvisionDataForRecordById(quizId, recordItemRequests);
        return new ResponseEntity<>(new ApiResponse<>(responses, "success"), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> deleteById(@RequestParam("id") String quizId){
        quizService.deleteById(quizId);
        return new ResponseEntity<>(new ApiResponse<>("", "success"), HttpStatus.OK);
    }

    @PutMapping("/internal")
    public ResponseEntity<ApiResponse<String>> updateQuiz(@RequestBody QuizGenerateResult quizGenerateResult){
        quizService.updateQuiz(quizGenerateResult);
        return new ResponseEntity<>(new ApiResponse<>("success", "success"), HttpStatus.OK);
    }

}
