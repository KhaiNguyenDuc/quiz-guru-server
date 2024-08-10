package com.quizguru.libraries.service.impl;

import com.quizguru.libraries.dto.request.WordRequest;
import com.quizguru.libraries.dto.request.WordSetRequest;
import com.quizguru.libraries.dto.response.PageResponse;
import com.quizguru.libraries.dto.response.WordResponse;
import com.quizguru.libraries.dto.response.WordSetResponse;
import com.quizguru.libraries.exception.AccessDeniedException;
import com.quizguru.libraries.exception.DefinitionNotFoundException;
import com.quizguru.libraries.exception.InvalidRequestException;
import com.quizguru.libraries.exception.ResourceNotFoundException;
import com.quizguru.libraries.mapper.WordMapper;
import com.quizguru.libraries.mapper.WordSetMapper;
import com.quizguru.libraries.model.Library;
import com.quizguru.libraries.model.Word;
import com.quizguru.libraries.model.WordSet;
import com.quizguru.libraries.properties.DictionaryProperties;
import com.quizguru.libraries.repository.LibraryRepository;
import com.quizguru.libraries.repository.WordRepository;
import com.quizguru.libraries.repository.WordSetRepository;
import com.quizguru.libraries.service.LibraryService;
import com.quizguru.libraries.utils.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LibraryServiceImpl implements LibraryService {

    private final LibraryRepository libraryRepository;
    private final WordSetRepository wordSetRepository;
    private final WordRepository wordRepository;
    private final DictionaryProperties dictionaryProperties;
    private final RestTemplate restTemplate;
    
    @Override
    public String createWordSet(WordSetRequest wordSetRequest) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Library> libraryOpt = libraryRepository.findByUserId(userId);
        if(libraryOpt.isEmpty()){
            throw new ResourceNotFoundException(Constant.RESOURCE_NOT_FOUND, "library", "user", userId);
        }
        if(Objects.isNull(wordSetRequest.words())){
            throw new InvalidRequestException(Constant.INVALID_REQUEST_MSG);
        }
        Library library = libraryOpt.get();
        library.setUserId(userId);
        WordSet wordSet;
        if(Objects.nonNull(wordSetRequest.name())){
            Optional<WordSet> wordSetOpt =
                    wordSetRepository.findByNameAndLibrary(wordSetRequest.name(), library);
            if(wordSetOpt.isPresent()){
                wordSet = wordSetOpt.get();
                wordSet.setWordNumber(wordSet.getWordNumber() + wordSetRequest.words().size());
            }else{
                wordSet = new WordSet();
                wordSet.setName(wordSetRequest.name());
                wordSet.setLibrary(library);
                wordSet.setWordNumber(wordSetRequest.words().size());
            }
        }else{
            wordSet = new WordSet();
            wordSet.setName("Untitled");
            wordSet.setLibrary(library);
            wordSet.setWordNumber(wordSetRequest.words().size());
        }

        if(wordSetRequest.quizId() != null){
                wordSet.setQuizId(wordSetRequest.quizId());
        }

        WordSet wordSetSaved = wordSetRepository.save(wordSet);
        List<Word> words = new ArrayList<>();

        for(WordRequest wordRequest : wordSetRequest.words()){
            Word word = new Word();
            if(Objects.nonNull(wordRequest.name())){
                word.setName(wordRequest.name().toLowerCase().trim());
            }
            if(Objects.nonNull(wordRequest.definition())){
                word.setDefinition(wordRequest.definition());
            }
            word.setWordSet(wordSetSaved);
            words.add(word);
        }
        wordRepository.saveAll(words);

        return wordSetSaved.getId();
    }

    @Override
    public WordSetResponse updateWordSet(WordSetRequest wordSetRequest, String wordSetId) {

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<WordSet> wordSetOpt = wordSetRepository.findById(wordSetId);

        if(wordSetOpt.isEmpty()){
            throw new ResourceNotFoundException(Constant.RESOURCE_NOT_FOUND, "wordSet", "wordSet", wordSetId);
        }

        WordSet wordSet = wordSetOpt.get();
        if(!userId.equals(wordSet.getLibrary().getUserId())){
            throw new AccessDeniedException(Constant.ACCESS_DENIED, "wordSet", userId);
        }
        wordSet.setName(wordSetRequest.name());
        WordSet wordSetSaved = wordSetRepository.save(wordSet);
        return WordSetMapper.toWordSetResponse(wordSetSaved);
    }

    @Override
    public PageResponse<WordSetResponse> findWordsById(String wordSetId, Pageable pageable) {

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<WordSet> wordSetOpt = wordSetRepository.findById(wordSetId);

        if(wordSetOpt.isEmpty()){
            throw new ResourceNotFoundException(Constant.RESOURCE_NOT_FOUND, "wordSet", "wordSet", wordSetId);
        }
        WordSet wordSet = wordSetOpt.get();
        if(!wordSet.getLibrary().getUserId().equals(userId)){
            throw  new AccessDeniedException(Constant.ACCESS_DENIED, "wordSet", userId);
        }

        Page<Word> words = wordRepository.findAllByWordSet(wordSet, pageable);
        wordSet.setWords(words.getContent());
        WordSetResponse wordSetResponse = WordSetMapper.toWordSetResponse(wordSet);

        return PageResponse.<WordSetResponse>builder()
                .content(wordSetResponse)
                .size(pageable.getPageSize())
                .page(pageable.getPageNumber())
                .totalElements(words.getNumberOfElements())
                .totalPages(words.getTotalPages())
                .build();

    }

    @Override
    public void deleteById(String wordSetId) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<WordSet> wordSetOpt = wordSetRepository.findById(wordSetId);

        if(wordSetOpt.isEmpty()){
            throw new ResourceNotFoundException(Constant.RESOURCE_NOT_FOUND, "wordSet", "wordSet", wordSetId);
        }
        WordSet wordSet = wordSetOpt.get();
        if(!wordSet.getLibrary().getUserId().equals(userId)){
            throw new AccessDeniedException(Constant.ACCESS_DENIED, "wordSet", userId);
        }

        wordSet.setIsDeleted(Boolean.TRUE);
        wordSetRepository.save(wordSet);
    }

    @Override
    public void bindQuiz(String wordSetId, String quizId) {

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<WordSet> wordSetOpt = wordSetRepository.findById(wordSetId);

        if(wordSetOpt.isEmpty()){
            throw new ResourceNotFoundException(Constant.RESOURCE_NOT_FOUND, "wordSet", "wordSet", wordSetId);
        }
        WordSet wordSet = wordSetOpt.get();
        if(!wordSet.getLibrary().getUserId().equals(userId)){
            throw new AccessDeniedException(Constant.ACCESS_DENIED, "wordSet", userId);
        }

        wordSet.setQuizId(quizId);
        wordSetRepository.save(wordSet);
    }

    @Override
    public List<WordResponse> addDefinition(String wordSetId, List<String> words) {
        List<String> lowercaseWords = words.stream()
                .map(String::toLowerCase)
                .map(String::trim)  // Remove leading and trailing spaces
                .toList();
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        List<WordResponse> wordResponses = new ArrayList<>();

        Optional<WordSet> wordSetOpt = wordSetRepository.findById(wordSetId);

        if(wordSetOpt.isEmpty()){
            throw new ResourceNotFoundException(Constant.RESOURCE_NOT_FOUND, "wordSet", "wordSet", wordSetId);
        }
        WordSet wordSet = wordSetOpt.get();
        if(!wordSet.getLibrary().getUserId().equals(userId)){
            throw new AccessDeniedException(Constant.ACCESS_DENIED, "wordSet", userId);
        }
        for (String word : lowercaseWords) {
            Optional<Word> wordOpt = wordRepository.findByNameAndWordSet(word, wordSet);
            if(wordOpt.isPresent()){
                Word w = wordOpt.get();
                try{
                    if(w.getDefinition().isEmpty()){
                        String url = String.format(dictionaryProperties.getUrl(), w.getName());
                        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
                        String responseBody = responseEntity.getBody();
                        log.error(responseBody);
                        if(Objects.nonNull(responseBody)){
                            w.setDefinition(responseBody);
                            wordRepository.save(w);
                        }
                    }
                    wordResponses.add(WordMapper.toWordResponse(w));
                } catch (HttpClientErrorException.NotFound e) {
                    throw new DefinitionNotFoundException(Constant.DEFINITION_NOT_FOUND, w.getName());
                }
            }
        }

        return wordResponses;
    }

    @Override
    public WordResponse updateWordDefinition(String wordId, WordRequest wordRequest) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Word> wordOpt = wordRepository.findById(wordId);
        if (wordOpt.isEmpty()) {
            throw new ResourceNotFoundException(Constant.RESOURCE_NOT_FOUND, "word", "word", wordId);
        }
        Word word = wordOpt.get();
        if(!userId.equals(word.getWordSet().getLibrary().getUserId())){
            throw new AccessDeniedException(Constant.ACCESS_DENIED, "wordSet", userId);
        }

        word.setContent(wordRequest.content());
        Word wordSaved = wordRepository.save(word);
        return WordMapper.toWordResponse(wordSaved);
    }

}