package com.quizguru.libraries.controller;

import com.quizguru.libraries.dto.request.BindRequest;
import com.quizguru.libraries.dto.request.WordRequest;
import com.quizguru.libraries.dto.request.WordSetRequest;
import com.quizguru.libraries.dto.response.ApiResponse;
import com.quizguru.libraries.dto.response.PageResponse;
import com.quizguru.libraries.dto.response.WordResponse;
import com.quizguru.libraries.dto.response.WordSetResponse;
import com.quizguru.libraries.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libraries")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    @PostMapping("/word-set/definition")
    public ResponseEntity<ApiResponse> addWordsDefinition(
            @RequestParam("id") String wordSetId,
            @RequestBody List<String> words
    ){

        List<WordResponse> wordResponses = libraryService.addDefinition(wordSetId, words);
        return new ResponseEntity<>(new ApiResponse(wordResponses, "success"), HttpStatus.OK);
    }

    @PutMapping("/word-set/definition")
    public ResponseEntity<ApiResponse> updateWordDefinition(
            @RequestParam(name ="id") String wordId,
            @RequestBody WordRequest wordRequest
    ){

        WordResponse  wordResponse = libraryService.updateWordDefinition(wordId, wordRequest);
        return new ResponseEntity<>(new ApiResponse(wordResponse, "success"), HttpStatus.OK);
    }

    @PostMapping("/word-set")
    public ResponseEntity<ApiResponse> createWordSet(
            @RequestBody WordSetRequest wordSetRequest
    ){
        String id = libraryService.createWordSet(wordSetRequest);
        return new ResponseEntity<>(new ApiResponse("success", id), HttpStatus.CREATED);
    }

    @PutMapping("/word-set")
    public ResponseEntity<ApiResponse> updateWordSet(
            @RequestBody WordSetRequest wordSetRequest,
            @RequestParam("id") String wordSetId
    ){
        WordSetResponse wordSetResponse = libraryService.updateWordSet(wordSetRequest, wordSetId);
        return new ResponseEntity<>(new ApiResponse(wordSetResponse, "success" ), HttpStatus.OK);
    }

    @GetMapping("/word-set")
    public ResponseEntity<PageResponse<WordSetResponse>> findWordsByWordSetId(
            @RequestParam("id") String wordSetId,
            @RequestParam(name = "page", defaultValue ="0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size
    ){
        PageResponse<WordSetResponse> words = libraryService.findWordsById(wordSetId, PageRequest.of(page, size));
        return new ResponseEntity<>(words, HttpStatus.OK);
    }

    @DeleteMapping("/word-set")
    public ResponseEntity<ApiResponse> deleteById(
            @RequestParam("id") String wordSetId
    ){
        libraryService.deleteById(wordSetId);
        return new ResponseEntity<>(new ApiResponse("", "success"), HttpStatus.OK);
    }

    @PostMapping("/word-set/bind")
    public ResponseEntity<ApiResponse> bindQuiz(
            @RequestBody BindRequest bindRequest
    ){
        libraryService.bindQuiz(bindRequest.getWordSetId(), bindRequest.getQuizId());
        return new ResponseEntity<>(new ApiResponse("success", "success"), HttpStatus.CREATED);
    }
}
