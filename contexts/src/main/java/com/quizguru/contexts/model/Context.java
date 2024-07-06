package com.quizguru.contexts.model;

import com.quizguru.contexts.model.enums.ContextType;
import com.quizguru.contexts.model.enums.Level;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "contexts")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Context{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "problem_number")
    private Integer problemNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private Level level;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ContextType type;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "language")
    public String language;

    @Column(name = "user_id")
    private String userId;
}