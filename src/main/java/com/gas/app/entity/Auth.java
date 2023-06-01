package com.gas.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;



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
