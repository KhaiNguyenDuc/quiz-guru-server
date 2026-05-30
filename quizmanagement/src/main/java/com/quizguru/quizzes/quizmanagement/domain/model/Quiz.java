package com.quizguru.quizzes.quizmanagement.domain.model;

import com.quizguru.quizzes.quizmanagement.domain.model.enums.Level;
import com.quizguru.quizzes.quizmanagement.domain.model.enums.QuizType;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class Quiz implements Serializable {

    private String userId;
    private String quizId;
    private String givenText;
    private List<Question> questions;
    private QuizType quizType;
    private Integer number;
    private String language;
    private Level level;
    private Integer duration;
    private Boolean isDeleted;


    @Builder(builderMethodName = "create")
    private Quiz(String userId, Integer number, Integer duration, Level level, QuizType quizType, String language, String givenText) {
        this.userId = userId;
        this.number = number;
        this.duration = duration;
        this.language = language;
        this.level = level;
        this.quizType = quizType;
        this.givenText = givenText;
    }

    @Builder(builderMethodName = "reconstitute")
    private Quiz(String quizId, String userId, Integer number, Integer duration, Level level, QuizType quizType, String language, String givenText, List<Question> questions) {
        this.quizId = quizId;
        this.userId = userId;
        this.number = number;
        this.duration = duration;
        this.language = language;
        this.level = level;
        this.quizType = quizType;
        this.givenText = givenText;
        this.questions = questions;
    }
}