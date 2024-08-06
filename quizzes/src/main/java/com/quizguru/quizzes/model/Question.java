package com.quizguru.quizzes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quizguru.quizzes.model.enums.QuestionType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "query")
    private String query;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Choice> choices;

    @Column(name = "explanation",  columnDefinition = "LONGTEXT")
    private String explanation;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private QuestionType type;

    @Transient
    @JsonIgnore
    private List<Integer> answers; // Not stored in the database but used for processing

    public void setAnswer(Integer answer, List<Choice> choices) {
        choices.get(answer).setIsCorrect(true);
    }

    @ManyToOne
    @JoinColumn(name="quiz_id",referencedColumnName = "id")
    @JsonIgnore
    private Quiz quiz;
}