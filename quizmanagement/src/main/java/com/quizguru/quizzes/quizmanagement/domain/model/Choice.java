package com.quizguru.quizzes.quizmanagement.domain.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Choice implements Serializable {

    private String id;
    private String name;
    private Question question;
    private Boolean isCorrect = false;

}