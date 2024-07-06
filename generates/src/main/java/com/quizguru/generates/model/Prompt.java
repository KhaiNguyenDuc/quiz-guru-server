package com.quizguru.generates.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "prompts")
public class Prompt {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Lob
    @Column(name = "given_text", columnDefinition = "LONGTEXT")
    private String givenText;

    @Column(name = "context_id")
    private String contextId;
}
