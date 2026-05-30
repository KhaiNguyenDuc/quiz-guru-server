package com.quizguru.quizzes.quizmanagement.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Choice implements Serializable {

    private String id;
    private String name;
    private Question question;
    private Boolean isCorrect = false;

}