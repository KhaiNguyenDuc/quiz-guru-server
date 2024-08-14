package com.quizguru.quizzes.controller;

import com.quizguru.quizzes.dto.request.text.RawFileRequest;
import com.quizguru.quizzes.dto.request.vocabulary.RawFileVocabRequest;
import com.quizguru.quizzes.dto.request.text.BaseRequest;
import com.quizguru.quizzes.dto.request.vocabulary.TextVocabRequest;
import com.quizguru.quizzes.dto.response.ApiResponse;
import com.quizguru.quizzes.dto.response.GenerateQuizResponse;
import com.quizguru.quizzes.dto.response.PageResponse;
import com.quizguru.quizzes.dto.response.QuizResponse;
import com.quizguru.quizzes.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PostMapping("/text")
    public ResponseEntity<ApiResponse> createQuizByText(
            @RequestBody BaseRequest baseRequest
    ){
        GenerateQuizResponse response = quizService.createQuizByText(baseRequest);
        return new ResponseEntity<>(new ApiResponse(response, "success"), HttpStatus.OK);
    }

    @PostMapping(value = "/doc", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ApiResponse> createQuizByDocFile(
            @ModelAttribute RawFileRequest rawFileRequest
    ){
        GenerateQuizResponse response = quizService.createQuizByDocFile(rawFileRequest);
        return new ResponseEntity<>(new ApiResponse(response, "success"), HttpStatus.OK);
    }

    @PostMapping(value = "/pdf", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ApiResponse> createQuizByPdfFile(
            @ModelAttribute RawFileRequest rawFileRequest
    ){
        GenerateQuizResponse response = quizService.createQuizByPdfFile(rawFileRequest);
        return new ResponseEntity<>(new ApiResponse(response, "success"), HttpStatus.OK);
    }

    @PostMapping(value = "/txt", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ApiResponse> createQuizByTxtFile(
            @ModelAttribute RawFileRequest rawFileRequest
    ){
        GenerateQuizResponse response = quizService.createQuizByTxtFile(rawFileRequest);
        return new ResponseEntity<>(new ApiResponse(response, "success"), HttpStatus.OK);
    }

    @PostMapping("/vocabulary/text")
    public ResponseEntity<ApiResponse> createVocabularyQuizByText(
            @RequestBody TextVocabRequest textVocabRequest
    ){
        GenerateQuizResponse response = quizService.createVocabularyQuizByText(textVocabRequest);
        return new ResponseEntity<>(new ApiResponse(response, "success"), HttpStatus.OK);
    }

    @PostMapping(value = "/vocabulary/doc", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE } )
    public ResponseEntity<ApiResponse> createVocabularyQuizByDocFile(
            @ModelAttribute RawFileVocabRequest rawFileVocabRequest
    ){
        GenerateQuizResponse response = quizService.createVocabularyQuizByDocFile(rawFileVocabRequest);
        return new ResponseEntity<>(new ApiResponse(response, "success"), HttpStatus.OK);
    }

    @PostMapping(value = "/vocabulary/pdf", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE } )
    public ResponseEntity<ApiResponse> createVocabularyQuizByPdfFile(
            @ModelAttribute RawFileVocabRequest rawFileVocabRequest
    ){
        GenerateQuizResponse response = quizService.createVocabularyQuizByPDFFile(rawFileVocabRequest);
        return new ResponseEntity<>(new ApiResponse(response, "success"), HttpStatus.OK);
    }

    @PostMapping(value = "/vocabulary/txt", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE } )
    public ResponseEntity<ApiResponse> createVocabularyQuizByTxt(
            @ModelAttribute RawFileVocabRequest rawFileVocabRequest
    ){
        GenerateQuizResponse response = quizService.createVocabularyQuizByTxtFile(rawFileVocabRequest);
        return new ResponseEntity<>(new ApiResponse(response, "success"), HttpStatus.OK);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<PageResponse<List<QuizResponse>>> findAllQuizByCurrentUser(
            @RequestParam(name = "id") String userId,
            @RequestParam(name = "page", defaultValue ="0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size
    ){
        PageResponse<List<QuizResponse>> response = quizService.findAllQuizByUserId(userId, PageRequest.of(page, size));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> findQuizById(@RequestParam("id") String quizId) {
        QuizResponse response = quizService.findQuizById(quizId);
        return new ResponseEntity<>(new ApiResponse(response, "success"), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteById(@RequestParam("id") String quizId){
        quizService.deleteById(quizId);
        return new ResponseEntity<>(new ApiResponse("", "success"), HttpStatus.OK);
    }
}
