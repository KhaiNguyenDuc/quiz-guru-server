package com.quizguru.quizzes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "choices")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Choice extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name="question_id",referencedColumnName = "id")
    @JsonIgnore
    private Question question;

    @Column(name = "is_correct")
    private Boolean isCorrect = false;

}