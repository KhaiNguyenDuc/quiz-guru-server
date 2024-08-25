package com.quizguru.libraries.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "word_sets")
@EqualsAndHashCode(callSuper = true)
public class WordSet extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "library_id", referencedColumnName = "id")
    private Library library;

    @OneToMany(mappedBy = "wordSet")
    private List<Word> words;

    @Column(name = "word_number")
    private Integer wordNumber;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Column(name = "quiz_id")
    private String quizId;

    @Column(name = "review_number")
    private Integer reviewNumber;

}
