package com.quizguru.quizzes.mapper;

import com.quizguru.quizzes.dto.request.text.BaseRequest;
import com.quizguru.quizzes.dto.request.text.HandledFileRequest;
import com.quizguru.quizzes.dto.request.text.RawFileRequest;
import com.quizguru.quizzes.dto.request.vocabulary.HandledFileVocabRequest;
import com.quizguru.quizzes.dto.request.vocabulary.RawFileVocabRequest;
import com.quizguru.quizzes.dto.request.vocabulary.TextVocabRequest;
import com.quizguru.quizzes.dto.response.GenerateQuizResponse;
import com.quizguru.quizzes.dto.response.QuizResponse;
import com.quizguru.quizzes.model.Quiz;
import com.quizguru.quizzes.model.enums.QuizType;
import com.quizguru.quizzes.model.enums.Level;
import com.quizguru.quizzes.utils.FileExtractor;

import java.util.ArrayList;
import java.util.List;

public class QuizMapper {
    public static Quiz generateRequestToQuiz(BaseRequest baseRequest){
        return Quiz.builder()
                .number(baseRequest.number())
                .duration(baseRequest.duration())
                .level(Level.valueOf(baseRequest.level()))
                .type(QuizType.valueOf(baseRequest.type()))
                .language(baseRequest.language())
                .givenText(baseRequest.content())
                .isDeleted(false)
                .build();
    }

    public static Quiz fileRequestToQuiz(RawFileRequest rawFileRequest) {
        return Quiz.builder()
                .number(rawFileRequest.number())
                .duration(rawFileRequest.duration())
                .level(Level.valueOf(rawFileRequest.level()))
                .type(QuizType.valueOf(rawFileRequest.type()))
                .language(rawFileRequest.language())
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

    public static Quiz fileVocabRequestToQuiz(RawFileVocabRequest rawFileVocabRequest){
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

    public static HandledFileRequest fileToDocFileRequest(RawFileRequest rawFileRequest){
        String fileContent = FileExtractor.extractDocxToString(rawFileRequest.file());
        return new HandledFileRequest(
                rawFileRequest.quizId(),
                rawFileRequest.type(),
                rawFileRequest.number(),
                rawFileRequest.language(),
                rawFileRequest.level(),
                rawFileRequest.duration(),
                fileContent
        );
    }

    public static HandledFileRequest fileToPdfFileRequest(RawFileRequest rawFileRequest){
        String fileContent = FileExtractor.extractPDFToString(rawFileRequest.file());
        return new HandledFileRequest(
                rawFileRequest.quizId(),
                rawFileRequest.type(),
                rawFileRequest.number(),
                rawFileRequest.language(),
                rawFileRequest.level(),
                rawFileRequest.duration(),
                fileContent
        );
    }

    public static HandledFileRequest fileToTxtFileRequest(RawFileRequest rawFileRequest) {
        String fileContent = FileExtractor.extractTxtToString(rawFileRequest.file());
        return new HandledFileRequest(
                rawFileRequest.quizId(),
                rawFileRequest.type(),
                rawFileRequest.number(),
                rawFileRequest.language(),
                rawFileRequest.level(),
                rawFileRequest.duration(),
                fileContent
        );
    }
}
