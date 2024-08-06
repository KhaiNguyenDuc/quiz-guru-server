package com.quizguru.quizzes.controller;

import com.quizguru.quizzes.dto.request.RawFileVocabRequest;
import com.quizguru.quizzes.dto.request.GenerateRequest;
import com.quizguru.quizzes.dto.request.TextVocabRequest;
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
    public ResponseEntity<GenerateQuizResponse> createQuizByText(
            @RequestBody GenerateRequest generateRequest
    ){
        GenerateQuizResponse response = quizService.createQuizByText(generateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/vocabulary")
    public ResponseEntity<GenerateQuizResponse> createVocabularyQuizByText(
            @RequestBody TextVocabRequest textVocabRequest
    ){
        GenerateQuizResponse response = quizService.createVocabularyQuizByText(textVocabRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/doc", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE } )
    public ResponseEntity<GenerateQuizResponse> createVocabularyQuizByDocFile(
            @ModelAttribute RawFileVocabRequest rawFileVocabRequest
    ){
        GenerateQuizResponse response = quizService.createVocabularyQuizByDocFile(rawFileVocabRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/pdf", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE } )
    public ResponseEntity<GenerateQuizResponse> createVocabularyQuizByPDFFile(
            @ModelAttribute RawFileVocabRequest rawFileVocabRequest
    ){
        GenerateQuizResponse response = quizService.createVocabularyQuizByPDFFile(rawFileVocabRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/txt", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE } )
    public ResponseEntity<GenerateQuizResponse> createVocabularyQuizByTxt(
            @ModelAttribute RawFileVocabRequest rawFileVocabRequest
    ){
        GenerateQuizResponse response = quizService.createVocabularyQuizByTxtFile(rawFileVocabRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/users/current")
    public ResponseEntity<PageResponse<List<QuizResponse>>> findAllQuizByCurrentUser(
            @RequestParam(name = "page", defaultValue ="0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size
    ){
        PageResponse<List<QuizResponse>> response = quizService.findAllQuizByCurrentUser(PageRequest.of(page, size));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<QuizResponse> findQuizById(@RequestParam("id") String quizId) {
        QuizResponse response = quizService.findQuizById(quizId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
