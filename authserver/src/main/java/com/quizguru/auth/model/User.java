package com.quizguru.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Table(name = "users")
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "email", unique = true, length = 30)
    private String email;

    @Column(name = "username", unique = true, length = 30)
    private String username;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "is_enabled")
    @Getter(AccessLevel.NONE)
    private Boolean isEnable;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private RefreshToken refreshToken;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private PasswordResetToken passwordResetToken;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    public Boolean isEnabled(){
        return this.isEnable;
    }

}
