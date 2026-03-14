package com.quizguru.generates.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizguru.generates.client.customer.CustomerClient;
import com.quizguru.generates.client.library.LibraryClient;
import com.quizguru.generates.client.library.dto.request.BindRequest;
import com.quizguru.generates.client.library.dto.request.WordRequest;
import com.quizguru.generates.client.library.dto.request.WordSetRequest;
import com.quizguru.generates.client.quiz.QuizClient;
import com.quizguru.generates.client.quiz.dto.request.QuizGenerateResult;
import com.quizguru.generates.client.quiz.dto.request.QuizRequest;
import com.quizguru.generates.dto.AIRequestBuilder;
import com.quizguru.generates.dto.AIRequestFactory;
import com.quizguru.generates.dto.request.*;
import com.quizguru.generates.dto.request.vocabulary.VocabularyPromptRequest;
import com.quizguru.generates.dto.response.AIResponse;
import com.quizguru.generates.dto.response.ApiResponse;
import com.quizguru.generates.exception.ResourceNotFoundException;
import com.quizguru.generates.properties.GenerateProperties;
import com.quizguru.generates.service.GenerateService;
import com.quizguru.generates.utils.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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

    private final GenerateProperties generateProperties;
    private final RestTemplate restTemplate;
    private final QuizClient quizClient;
    private final LibraryClient libraryClient;
    private final CustomerClient customerClient;
    private final ObjectMapper objectMapper;

    @Override
    public void generateQuiz(ChatRequest chat, String userId) {
        try {
            this.setSecurityContextFromHeaders(userId);

            String provider = chat.getGenerateConfiguration() != null && chat.getGenerateConfiguration().getProvider() != null && !chat.getGenerateConfiguration().getProvider().isBlank()
                    ? chat.getGenerateConfiguration().getProvider()
                    : generateProperties.getProvider();

            GenerateProperties.Provider providerConfig = generateProperties.resolve(provider);

            AIRequestBuilder builder = AIRequestFactory.getBuilder(provider);
            Object request = builder.buildRequest(chat);
            HttpHeaders headers = builder.buildHeaders(providerConfig.getApiKey());
            HttpEntity<Object> httpEntity = new HttpEntity<>(request, headers);

            AIResponse aiResponse = (AIResponse) restTemplate.postForObject(
                    providerConfig.getApiURL(),
                    httpEntity,
                    builder.getResponseType()
            );
            log.info(chat.getPromptRequest().getText());
            log.info(chat.getPromptRequest().generatePrompt(chat.getPromptConfiguration()));
            if(Objects.nonNull(aiResponse)){
                String content = aiResponse.getContent();
                if(Objects.nonNull(content)){
                    String sanitizedContent = sanitizeJsonContent(content);

                    PromptRequest promptRequest = chat.getPromptRequest();
                    QuizRequest quizRequest = objectMapper.readValue(sanitizedContent, QuizRequest.class);

                    QuizGenerateResult quizGenerateResult = QuizGenerateResult.builder()
                            .quizRequest(quizRequest)
                            .quizId(promptRequest.getQuizId())
                            .build();

                    log.info(quizGenerateResult.toString());
                    quizClient.updateQuiz(quizGenerateResult);

                    if (chat.getPromptRequest() instanceof VocabularyPromptRequest){
                        this.generateWordSet(chat, sanitizedContent);
                    }
                }
            }

        } catch (Exception e){
            log.info(e.getMessage());
        }
    }

    @Override
    public void generateWordSet(ChatRequest chat, String responseContent) {
        if (chat.getPromptRequest() instanceof VocabularyPromptRequest vocabularyPromptRequest){
            try{
                String userId = SecurityContextHolder.getContext().getAuthentication().getName();
                String sanitizedContent = sanitizeJsonContent(responseContent);
                JsonNode jsonNode = objectMapper.readTree(sanitizedContent);
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
                            .userId(userId)
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
                            .userId(userId)
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

    private String sanitizeJsonContent(String content) {
        if (content == null) {
            return null;
        }

        String sanitized = content.trim();
        sanitized = sanitized.replaceFirst("^```(?:json)?\\s*", "");
        sanitized = sanitized.replaceFirst("^``(?:json)?\\s*", "");
        sanitized = sanitized.replaceFirst("^`(?:json)?\\s*", "");
        sanitized = sanitized.replaceFirst("\\s*```$", "");
        return sanitized.trim();
    }

     public void setSecurityContextFromHeaders(String userId) {

        String roles = "";
        ApiResponse<List<String>> apiResponse = customerClient.findRoleFromUserId(userId).getBody();
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
