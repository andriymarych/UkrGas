package com.gas.app.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Entity
@Table(name = "auth")
@Getter @Setter
@ToString
@NoArgsConstructor
public class Auth {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    public Auth(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
