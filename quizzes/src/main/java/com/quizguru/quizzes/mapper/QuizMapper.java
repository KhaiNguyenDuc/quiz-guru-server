package com.quizguru.quizzes.mapper;

import com.quizguru.quizzes.dto.request.*;
import com.quizguru.quizzes.dto.response.GenerateQuizResponse;
import com.quizguru.quizzes.dto.response.QuizResponse;
import com.quizguru.quizzes.model.Quiz;
import com.quizguru.quizzes.model.enums.QuizType;
import com.quizguru.quizzes.model.enums.Level;
import com.quizguru.quizzes.utils.FileExtractor;

import java.util.ArrayList;
import java.util.List;

public class QuizMapper {
    public static Quiz generateRequestToQuiz(GenerateRequest generateRequest){
        return Quiz.builder()
                .number(generateRequest.number())
                .duration(generateRequest.duration())
                .level(Level.valueOf(generateRequest.level()))
                .type(QuizType.valueOf(generateRequest.type()))
                .language(generateRequest.language())
                .givenText(generateRequest.content())
                .isDeleted(false)
                .build();
    }

    public static GenerateQuizResponse quizToGenerateQuizResponse(Quiz quiz){
        return GenerateQuizResponse.builder()
                .id(quiz.getId())
                .problemNumber(quiz.getNumber())
                .duration(quiz.getDuration())
                .level(quiz.getLevel().getValue())
                .type(quiz.getType().getValue())
                .language(quiz.getLanguage())
                .givenText(quiz.getGivenText())
                .build();
    }

    public static Quiz textVocabRequestToQuiz(TextVocabRequest textVocabRequest) {
        return Quiz.builder()
                .number(textVocabRequest.number())
                .duration(textVocabRequest.duration())
                .level(Level.valueOf(textVocabRequest.level()))
                .type(QuizType.valueOf(textVocabRequest.type()))
                .language(textVocabRequest.language())
                .givenText(textVocabRequest.names().toString())
                .isDeleted(false)
                .build();
    }

    public static Quiz docFileVocabRequestToQuiz(RawFileVocabRequest rawFileVocabRequest){
        return Quiz.builder()
                .number(rawFileVocabRequest.number())
                .duration(rawFileVocabRequest.duration())
                .level(Level.valueOf(rawFileVocabRequest.level()))
                .type(QuizType.valueOf(rawFileVocabRequest.type()))
                .language(rawFileVocabRequest.language())
                .isDeleted(false)
                .build();
    }

    public static HandledFileVocabRequest fileVocabToDocFileVocabRequest(RawFileVocabRequest rawFileVocabRequest){
        String fileContent = FileExtractor.extractDocxToString(rawFileVocabRequest.file());
        return new HandledFileVocabRequest(
                rawFileVocabRequest.quizId(),
                rawFileVocabRequest.type(),
                rawFileVocabRequest.number(),
                rawFileVocabRequest.language(),
                rawFileVocabRequest.level(),
                rawFileVocabRequest.duration(),
                fileContent
        );
    }

    public static HandledFileVocabRequest fileVocabToPdfFileVocabRequest(RawFileVocabRequest rawFileVocabRequest) {
        String fileContent = FileExtractor.extractPDFToString(rawFileVocabRequest.file());
        return new HandledFileVocabRequest(
                rawFileVocabRequest.quizId(),
                rawFileVocabRequest.type(),
                rawFileVocabRequest.number(),
                rawFileVocabRequest.language(),
                rawFileVocabRequest.level(),
                rawFileVocabRequest.duration(),
                fileContent
        );
    }

    public static HandledFileVocabRequest fileVocabToTxtFileVocabRequest(RawFileVocabRequest rawFileVocabRequest) {
        String fileContent = FileExtractor.extractTxtToString(rawFileVocabRequest.file());
        return new HandledFileVocabRequest(
                rawFileVocabRequest.quizId(),
                rawFileVocabRequest.type(),
                rawFileVocabRequest.number(),
                rawFileVocabRequest.language(),
                rawFileVocabRequest.level(),
                rawFileVocabRequest.duration(),
                fileContent
        );
    }

    public static List<QuizResponse> quizzesToQuizResponses(List<Quiz> quizzes) {
        List<QuizResponse> quizResponses = new ArrayList<>();
        for (Quiz quiz : quizzes) {
            QuizResponse quizResponse = QuizResponse.builder()
                    .id(quiz.getId())
                    .problemNumber(quiz.getNumber())
                    .duration(quiz.getDuration())
                    .level(quiz.getLevel().getValue())
                    .type(quiz.getType().getValue())
                    .language(quiz.getLanguage())
                    .givenText(quiz.getGivenText())
                    .build();
            quizResponses.add(quizResponse);
        }
        return quizResponses;
    }

    public static QuizResponse quizToQuizResponse(Quiz quiz) {
        return QuizResponse.builder()
                .id(quiz.getId())
                .problemNumber(quiz.getNumber())
                .duration(quiz.getDuration())
                .level(quiz.getLevel().getValue())
                .type(quiz.getType().getValue())
                .language(quiz.getLanguage())
                .givenText(quiz.getGivenText())
                .build();
    }
}
