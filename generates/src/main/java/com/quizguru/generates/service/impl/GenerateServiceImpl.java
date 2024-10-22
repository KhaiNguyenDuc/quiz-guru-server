package com.quizguru.generates.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizguru.generates.client.auth.AuthClient;
import com.quizguru.generates.client.library.LibraryClient;
import com.quizguru.generates.client.library.dto.request.BindRequest;
import com.quizguru.generates.client.library.dto.request.WordRequest;
import com.quizguru.generates.client.library.dto.request.WordSetRequest;
import com.quizguru.generates.client.quiz.QuizClient;
import com.quizguru.generates.client.quiz.dto.request.QuizGenerateResult;
import com.quizguru.generates.client.quiz.dto.request.QuizRequest;
import com.quizguru.generates.dto.Message;
import com.quizguru.generates.dto.request.*;
import com.quizguru.generates.dto.request.vocabulary.VocabularyPromptRequest;
import com.quizguru.generates.dto.response.ApiResponse;
import com.quizguru.generates.exception.ResourceNotFoundException;
import com.quizguru.generates.properties.GenerateProperties;
import com.quizguru.generates.dto.response.ChatResponse;
import com.quizguru.generates.service.GenerateService;
import com.quizguru.generates.utils.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
        public class GenerateServiceImpl implements GenerateService {

    private final RestTemplate restTemplate;
    private final QuizClient quizClient;
    private final LibraryClient libraryClient;
    private final AuthClient authClient;
    private final ObjectMapper objectMapper;

    @Override
    public void generateQuiz(ChatRequest chat, String userId) {
        try {
            this.setSecurityContextFromHeaders(userId);
            GenerateProperties generateConfiguration = chat.getGenerateConfiguration();
            ChatResponse chatResponse = restTemplate.postForObject(generateConfiguration.getApiURL(), chat, ChatResponse.class);
            log.info(chat.getPromptRequest().getText());
            log.info(chat.getPromptRequest().generatePrompt(chat.getPromptConfiguration()));
            if(Objects.nonNull(chatResponse) && Objects.nonNull(chatResponse.getChoices())){
                Message message = chatResponse.getChoices().get(0).getMessage();
                if(Objects.nonNull(message)){

                    PromptRequest promptRequest = chat.getPromptRequest();
                    QuizRequest quizRequest = objectMapper.readValue(message.getContent(), QuizRequest.class);

                    QuizGenerateResult quizGenerateResult = QuizGenerateResult.builder()
                            .quizRequest(quizRequest)
                            .quizId(promptRequest.getQuizId())
                            .build();

                    log.info(quizGenerateResult.toString());
                    quizClient.updateQuiz(quizGenerateResult);

                    if (chat.getPromptRequest() instanceof VocabularyPromptRequest){
                        this.generateWordSet(chat, chatResponse);
                    }
                }
            }

        } catch (Exception e){
            log.info(e.getMessage());
        }
    }

    @Override
    public void generateWordSet(ChatRequest chat, ChatResponse chatResponse) {
        if (chat.getPromptRequest() instanceof VocabularyPromptRequest vocabularyPromptRequest){
            try{
                String stringResponse = chatResponse.getChoices().get(0).getMessage().getContent();
                JsonNode jsonNode = objectMapper.readTree(stringResponse);
                JsonNode wordNode = jsonNode.get("words");
                List<WordRequest> wordRequests = new ArrayList<>();

                for(JsonNode word: wordNode){
                    WordRequest wordRequest = WordRequest.builder()
                            .name(word.asText())
                            .build();
                    wordRequests.add(wordRequest);
                }

                if(Objects.isNull(vocabularyPromptRequest.wordSetId) || vocabularyPromptRequest.wordSetId.isEmpty()){
                    WordSetRequest wordSetRequest = WordSetRequest.builder()
                            .name(vocabularyPromptRequest.wordSetName)
                            .words(wordRequests)
                            .quizId(vocabularyPromptRequest.quizId)
                            .build();
                    libraryClient.createWordSet(wordSetRequest);
                }else {
                    WordSetRequest wordSetRequest = WordSetRequest.builder()
                            .id(vocabularyPromptRequest.wordSetId)
                            .words(wordRequests)
                            .quizId(vocabularyPromptRequest.quizId)
                            .build();

                    libraryClient.addWordToWordSet(wordSetRequest);
                    BindRequest bindRequest = BindRequest.builder()
                            .wordSetId(vocabularyPromptRequest.wordSetId)
                            .quizId(vocabularyPromptRequest.quizId)
                            .build();
                    libraryClient.bindQuiz(bindRequest);
                }
            }catch (ResponseStatusException ex){
                if(ex.getStatusCode() == HttpStatus.NOT_FOUND){
                    throw new ResourceNotFoundException(Constant.ERROR_CODE.RESOURCE_NOT_FOUND, "wordSet", "wordSet", vocabularyPromptRequest.wordSetId);
                }
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
            } catch (Exception e){
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }

        }
    }

     public void setSecurityContextFromHeaders(String userId) {

        String roles = "";
        ApiResponse<List<String>> apiResponse = authClient.findRoleFromUserId(userId).getBody();
        if(Objects.nonNull(apiResponse)){
            roles = String.valueOf(apiResponse.data());
        }
        if (userId != null && Objects.nonNull(roles)) {

            List<SimpleGrantedAuthority> authorities = Arrays.stream(roles.split(","))
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.trim()))
                    .collect(Collectors.toList());

            UserDetails userDetails = User.builder()
                    .username(userId)
                    .password("PROTECTED")
                    .authorities(authorities)
                    .build();

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }
}
