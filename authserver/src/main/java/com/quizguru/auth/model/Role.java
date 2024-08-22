package com.quizguru.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quizguru.auth.model.enums.RoleName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.util.List;

@Getter
@Setter
@Table(name = "roles")
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    @NaturalId
    private RoleName name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private List<User> users;
}
