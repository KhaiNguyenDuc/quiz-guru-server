package com.quizguru.records.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RecordItemChoiceId implements Serializable {

    private String recordItem;
    private String choiceId;
}