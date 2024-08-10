package com.quizguru.records.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "records")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToMany(mappedBy = "record", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<RecordItem> recordItems;

    @Column(name = "score")
    private Integer score;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "time_left")
    private Integer timeLeft;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "quiz_id")
    private String quizId;
}
