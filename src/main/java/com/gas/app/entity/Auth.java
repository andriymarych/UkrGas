package com.gas.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;



@Entity
@Table(name = "auth")
@Data
@NoArgsConstructor
public class Auth {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToOne(fetch = FetchType.LAZY, optional=false)
    @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id",foreignKey = @ForeignKey(name = "FK_Auth_UserId"))
    @JsonBackReference
    private User user;

    public Auth(String email, User user) {
        this.email = email;
        this.user = user;
    }
}
