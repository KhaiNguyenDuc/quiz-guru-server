package com.quizguru.quizzes.model;

import com.quizguru.quizzes.model.enums.QuizType;
import com.quizguru.quizzes.model.enums.Level;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "quizzes")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Lob
    @Column(name = "given_text", columnDefinition = "LONGTEXT")
    private String givenText;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.EAGER)
    private List<Question> questions;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private QuizType type;

    @Column(name = "number")
    private Integer number;

    @Column(name = "language")
    private String language;

    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private Level level;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "word_set_id")
    private String wordSetId;
}