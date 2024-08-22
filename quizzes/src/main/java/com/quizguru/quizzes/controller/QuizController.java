package com.quizguru.quizzes.controller;

import com.quizguru.quizzes.dto.request.QuizGenerateResult;
import com.quizguru.quizzes.dto.request.text.RawFileRequest;
import com.quizguru.quizzes.dto.request.vocabulary.ListToVocabRequest;
import com.quizguru.quizzes.dto.request.vocabulary.RawFileVocabRequest;
import com.quizguru.quizzes.dto.request.text.BaseRequest;
import com.quizguru.quizzes.dto.request.vocabulary.TextVocabRequest;
import com.quizguru.quizzes.dto.response.*;
import com.quizguru.quizzes.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

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
           @CurrentSecurityContext(expression = "authentication.principal") UserDetails userDetails,
            @RequestParam(name = "page", defaultValue ="0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size
    ){
        PageResponse<List<QuizResponse>> response = quizService.findAllQuizByUserId(userDetails.getUsername(), PageRequest.of(page, size));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<QuizResponse>> findQuizById(@RequestParam("id") String quizId) {
        QuizResponse response = quizService.findQuizById(quizId);
        return new ResponseEntity<>(new ApiResponse<>(response, "success"), HttpStatus.OK);
    }

    @GetMapping("/detail")
    public ResponseEntity<ApiResponse<DetailQuizResponse>> findDetailQuizById(@RequestParam("id") String quizId) {
        DetailQuizResponse response = quizService.findDetailQuizById(quizId);
        return new ResponseEntity<>(new ApiResponse<>(response, "success"), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> deleteById(@RequestParam("id") String quizId){
        quizService.deleteById(quizId);
        return new ResponseEntity<>(new ApiResponse<>("", "success"), HttpStatus.OK);
    }

    @PutMapping("/internal/create")
    public void updateQuiz(@RequestBody QuizGenerateResult quizGenerateResult){
        quizService.updateQuiz(quizGenerateResult);
    }
}
