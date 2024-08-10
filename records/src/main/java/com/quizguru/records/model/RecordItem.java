package com.quizguru.records.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "record_item")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id", referencedColumnName = "id")
    @JsonIgnore
    private Record record;

    @Column(name = "question_id")
    private String questionId;

    @Column(name = "explanation")
    private String explanation;

    @ElementCollection
    @CollectionTable(name = "record_item_choice", joinColumns = @JoinColumn(name = "record_item_id"))
    @Column(name = "choice_id")
    private List<String> selectedChoices;

}
