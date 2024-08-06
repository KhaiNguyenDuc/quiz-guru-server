package com.quizguru.records.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "record_item")
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

    @ElementCollection
    @CollectionTable(name = "record_item_choice", joinColumns = @JoinColumn(name = "record_item_id"))
    @Column(name = "choice_id")
    private List<String> selectedChoices;

}