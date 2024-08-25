package com.quizguru.libraries.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "words")
@EqualsAndHashCode(callSuper = true)
public class Word extends DateAudit{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "definition", columnDefinition = "LONGTEXT")
    private String definition;

    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "word_set_id", referencedColumnName = "id")
    private WordSet wordSet;
}
