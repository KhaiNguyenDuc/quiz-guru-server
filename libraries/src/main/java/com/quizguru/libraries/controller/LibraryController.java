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
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libraries")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    @PostMapping("/internal")
    public ResponseEntity<ApiResponse<String>> create(@RequestParam("userId") String userId){
        String libraryId = libraryService.create(userId);
        return new ResponseEntity<>(new ApiResponse<>(libraryId, "success"), HttpStatus.OK);
    }

    @PostMapping("/word/definition")
    public ResponseEntity<ApiResponse<List<WordResponse>>> addWordsDefinition(
            @RequestParam("id") String wordSetId,
            @RequestBody List<String> words
    ){

        List<WordResponse> wordResponses = libraryService.addDefinition(wordSetId, words);
        return new ResponseEntity<>(new ApiResponse<>(wordResponses, "success"), HttpStatus.OK);
    }

    @PutMapping("/word/definition")
    public ResponseEntity<ApiResponse<WordResponse>> updateWordDefinition(
            @RequestParam(name ="id") String wordId,
            @RequestBody WordRequest wordRequest
    ){

        WordResponse wordResponse = libraryService.updateWordDefinition(wordId, wordRequest);
        return new ResponseEntity<>(new ApiResponse<>(wordResponse, "success"), HttpStatus.OK);
    }

    @PostMapping("/word-set")
    public ResponseEntity<ApiResponse<String>> createWordSet(
            @RequestBody WordSetRequest wordSetRequest
    ){
        String id = libraryService.createWordSet(wordSetRequest);
        return new ResponseEntity<>(new ApiResponse<>(id, "success"), HttpStatus.CREATED);
    }

    @PutMapping("/word-set")
    public ResponseEntity<ApiResponse<WordSetResponse>> updateWordSet(
            @RequestBody WordSetRequest wordSetRequest,
            @RequestParam("id") String wordSetId
    ){
        WordSetResponse wordSetResponse = libraryService.updateWordSet(wordSetRequest, wordSetId);
        return new ResponseEntity<>(new ApiResponse<>(wordSetResponse, "success" ), HttpStatus.OK);
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
    public ResponseEntity<ApiResponse<String>> deleteById(
            @RequestParam("id") String wordSetId
    ){
        libraryService.deleteById(wordSetId);
        return new ResponseEntity<>(new ApiResponse<>("", "success"), HttpStatus.OK);
    }

    @PostMapping("/internal/word-set/bind")
    public ResponseEntity<ApiResponse<String>> bindQuiz(
            @RequestBody BindRequest bindRequest
    ){
        libraryService.bindQuiz(bindRequest.wordSetId(), bindRequest.quizId());
        return new ResponseEntity<>(new ApiResponse<>("success", "success"), HttpStatus.CREATED);
    }

    @PostMapping("/internal/word-set/add")
    public ResponseEntity<ApiResponse<String>> addWordToWordSet(
            @RequestBody WordSetRequest wordSetRequest
    ){
        libraryService.addWordToWordSet(wordSetRequest);
        return new ResponseEntity<>(new ApiResponse<>("success", "success"), HttpStatus.CREATED);
    }

    @GetMapping("/word-set/current")
    public ResponseEntity<PageResponse<List<WordSetResponse>>> findCurrentUserWordSets(
            @CurrentSecurityContext(expression = "authentication.principal") UserDetails userDetails,
            @RequestParam(name = "page", defaultValue ="0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size) {
        PageResponse<List<WordSetResponse>> wordSetResponses = libraryService.findCurrentUserWordSets(userDetails.getUsername(), PageRequest.of(page, size));
        return new ResponseEntity<>(wordSetResponses, HttpStatus.OK);
    }

    @GetMapping("/internal/word-set")
    public ResponseEntity<ApiResponse<WordSetResponse>> findWordSetByQuizId(@RequestParam("quizId") String quizId){
        WordSetResponse wordSetResponses = libraryService.findWordSetByQuizId(quizId);
        return new ResponseEntity<>(new ApiResponse<>(wordSetResponses, "success"), HttpStatus.OK);
    }


    @DeleteMapping("/internal/remove/word-set")
    ResponseEntity<ApiResponse<String>> removeQuiz(@RequestParam("quizId") String quizId){
        libraryService.removeQuizByQuizId(quizId);
        return new ResponseEntity<>(new ApiResponse<>("success", "success"), HttpStatus.OK);
    }

    @PutMapping("/internal/review")
    ResponseEntity<ApiResponse<String>> increaseReviewTime(@RequestParam("quizId") String quizId){
        libraryService.increaseReviewTime(quizId);

        return new ResponseEntity<>(new ApiResponse<>("success", "success"), HttpStatus.OK);
    }
}
