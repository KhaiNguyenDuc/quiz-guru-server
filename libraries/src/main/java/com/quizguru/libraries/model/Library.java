package com.quizguru.libraries.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "libraries")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Library extends DateAudit{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @JsonIgnore
    private String userId;

    @OneToMany(mappedBy = "library")
    private List<WordSet> wordSets;
}
