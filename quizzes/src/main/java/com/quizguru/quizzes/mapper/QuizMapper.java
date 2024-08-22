package com.quizguru.quizzes.mapper;

import com.quizguru.quizzes.dto.request.QuestionRequest;
import com.quizguru.quizzes.dto.request.text.BaseRequest;
import com.quizguru.quizzes.dto.request.text.HandledFileRequest;
import com.quizguru.quizzes.dto.request.text.RawFileRequest;
import com.quizguru.quizzes.dto.request.vocabulary.HandledFileVocabRequest;
import com.quizguru.quizzes.dto.request.vocabulary.ListToVocabRequest;
import com.quizguru.quizzes.dto.request.vocabulary.RawFileVocabRequest;
import com.quizguru.quizzes.dto.request.vocabulary.TextVocabRequest;
import com.quizguru.quizzes.dto.response.*;
import com.quizguru.quizzes.model.Choice;
import com.quizguru.quizzes.model.Question;
import com.quizguru.quizzes.model.Quiz;
import com.quizguru.quizzes.model.enums.QuizType;
import com.quizguru.quizzes.model.enums.Level;
import com.quizguru.quizzes.utils.FileExtractor;

import java.util.ArrayList;
import java.util.List;

public class QuizMapper {
    public static Quiz toQuiz(BaseRequest baseRequest){
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

    public static Quiz toQuiz(RawFileRequest rawFileRequest) {
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

    public static Quiz toQuiz(ListToVocabRequest listVocabRequest) {
        return Quiz.builder()
                .number(listVocabRequest.number())
                .duration(listVocabRequest.duration())
                .level(Level.valueOf(listVocabRequest.level()))
                .type(QuizType.valueOf(listVocabRequest.type()))
                .language(listVocabRequest.language())
                .givenText(listVocabRequest.names().toString())
                .isDeleted(false)
                .build();
    }

    public static Quiz toQuiz(TextVocabRequest textVocabRequest) {
        return Quiz.builder()
                .number(textVocabRequest.number())
                .duration(textVocabRequest.duration())
                .level(Level.valueOf(textVocabRequest.level()))
                .type(QuizType.valueOf(textVocabRequest.type()))
                .language(textVocabRequest.language())
                .givenText(textVocabRequest.content())
                .isDeleted(false)
                .build();
    }

    public static Quiz toQuiz(RawFileVocabRequest rawFileVocabRequest){
        return Quiz.builder()
                .number(rawFileVocabRequest.number())
                .duration(rawFileVocabRequest.duration())
                .level(Level.valueOf(rawFileVocabRequest.level()))
                .type(QuizType.valueOf(rawFileVocabRequest.type()))
                .language(rawFileVocabRequest.language())
                .isDeleted(false)
                .build();
    }


    public static HandledFileVocabRequest toDocFileVocabRequest(RawFileVocabRequest rawFileVocabRequest, String quizId){
        String fileContent = FileExtractor.extractDocxToString(rawFileVocabRequest.file());
        return new HandledFileVocabRequest(
                rawFileVocabRequest.userId(),
                quizId,
                rawFileVocabRequest.wordSetId(),
                rawFileVocabRequest.wordSetName(),
                rawFileVocabRequest.type(),
                rawFileVocabRequest.number(),
                rawFileVocabRequest.language(),
                rawFileVocabRequest.level(),
                rawFileVocabRequest.duration(),
                fileContent
        );
    }

    public static HandledFileVocabRequest toPdfFileVocabRequest(RawFileVocabRequest rawFileVocabRequest, String quizId) {
        String fileContent = FileExtractor.extractPDFToString(rawFileVocabRequest.file());
        return new HandledFileVocabRequest(
                rawFileVocabRequest.userId(),
                quizId,
                rawFileVocabRequest.wordSetId(),
                rawFileVocabRequest.wordSetName(),
                rawFileVocabRequest.type(),
                rawFileVocabRequest.number(),
                rawFileVocabRequest.language(),
                rawFileVocabRequest.level(),
                rawFileVocabRequest.duration(),
                fileContent
        );
    }

    public static HandledFileVocabRequest toTxtFileVocabRequest(RawFileVocabRequest rawFileVocabRequest, String quizId) {
        String fileContent = FileExtractor.extractTxtToString(rawFileVocabRequest.file());
        return new HandledFileVocabRequest(
                rawFileVocabRequest.userId(),
                quizId,
                rawFileVocabRequest.wordSetId(),
                rawFileVocabRequest.wordSetName(),
                rawFileVocabRequest.type(),
                rawFileVocabRequest.number(),
                rawFileVocabRequest.language(),
                rawFileVocabRequest.level(),
                rawFileVocabRequest.duration(),
                fileContent
        );
    }

    public static List<QuizResponse> toQuizResponses(List<Quiz> quizzes) {
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

    public static QuizResponse toQuizResponse(Quiz quiz) {
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

    public static HandledFileRequest toDocFileRequest(RawFileRequest rawFileRequest, String quizId){
        String fileContent = FileExtractor.extractDocxToString(rawFileRequest.file());
        return new HandledFileRequest(
                rawFileRequest.userId(),
                quizId,
                rawFileRequest.type(),
                rawFileRequest.number(),
                rawFileRequest.language(),
                rawFileRequest.level(),
                rawFileRequest.duration(),
                fileContent
        );
    }

    public static HandledFileRequest toPdfFileRequest(RawFileRequest rawFileRequest, String quizId){
        String fileContent = FileExtractor.extractPDFToString(rawFileRequest.file());
        return new HandledFileRequest(
                rawFileRequest.userId(),
                quizId,
                rawFileRequest.type(),
                rawFileRequest.number(),
                rawFileRequest.language(),
                rawFileRequest.level(),
                rawFileRequest.duration(),
                fileContent
        );
    }

    public static HandledFileRequest toTxtFileRequest(RawFileRequest rawFileRequest, String quizId) {
        String fileContent = FileExtractor.extractTxtToString(rawFileRequest.file());
        return new HandledFileRequest(
                rawFileRequest.userId(),
                quizId,
                rawFileRequest.type(),
                rawFileRequest.number(),
                rawFileRequest.language(),
                rawFileRequest.level(),
                rawFileRequest.duration(),
                fileContent
        );
    }

    public static List<Question> toQuestions(List<QuestionRequest> questionRequests, Quiz quiz) {
        List<Question> questions = new ArrayList<>();
        for (QuestionRequest questionRequest : questionRequests) {
            Question question = Question.builder()
                    .quiz(quiz)
                    .answers(questionRequest.answers())
                    .query(questionRequest.query())
                    .explanation(questionRequest.explanation())
                    .type(questionRequest.type())
                    .build();

            List<Choice> choices = QuizMapper.toChoices(questionRequest.choices(), question);
            question.setChoices(choices);
            questions.add(question);
        }
        return questions;
    }

    public static List<Choice> toChoices(List<String> choiceNames, Question question){
        List<Choice> choices = new ArrayList<>();
        for (String name : choiceNames) {
            Choice choice = Choice.builder()
                    .isCorrect(false) // Update later
                    .question(question)
                    .name(name)
                    .build();
            choices.add(choice);
        }
        return choices;
    }

    public static DetailQuizResponse toDetailQuizResponse(Quiz quiz, WordSetResponse wordSetResponse) {
        return DetailQuizResponse.builder()
                .id(quiz.getId())
                .duration(quiz.getDuration())
                .wordSet(wordSetResponse)
                .type(quiz.getType().getValue())
                .language(quiz.getLanguage())
                .givenText(quiz.getGivenText())
                .questions(QuizMapper.toQuestionResponses(quiz.getQuestions()))
                .build();
    }


    private static QuestionResponse toQuestionResponse(Question question) {
         return QuestionResponse.builder()
                .id(question.getId())
                .type(question.getType().getValue())
                .query(question.getQuery())
                .explanation(question.getExplanation())
                .choices(QuizMapper.toChoiceResponses(question.getChoices()))
                .build();

    }

    private static List<QuestionResponse> toQuestionResponses(List<Question> questions) {
        List<QuestionResponse> questionResponses = new ArrayList<>();
        for (Question question : questions) {
            QuestionResponse questionResponse = QuizMapper.toQuestionResponse(question);
            questionResponses.add(questionResponse);
        }
        return questionResponses;
    }

    private static ChoiceResponse toChoiceResponse(Choice choice) {
        return ChoiceResponse.builder()
                .id(choice.getId())
                .isCorrect(choice.getIsCorrect())
                .name(choice.getName())
                .build();
    }

    private static List<ChoiceResponse> toChoiceResponses(List<Choice> choices) {
        List<ChoiceResponse> choiceResponses = new ArrayList<>();
        for (Choice choice : choices) {
            ChoiceResponse choiceResponse = QuizMapper.toChoiceResponse(choice);
            choiceResponses.add(choiceResponse);
        }
        return choiceResponses;
    }


}
