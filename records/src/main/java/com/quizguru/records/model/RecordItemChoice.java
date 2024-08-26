package com.quizguru.records.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "record_item_choice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@IdClass(RecordItemChoiceId.class)
public class RecordItemChoice extends DateAudit {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_item_id", referencedColumnName = "id")
    @JsonIgnore
    private RecordItem recordItem;

    @Id
    @Column(name = "choice_id")
    private String choiceId;
}