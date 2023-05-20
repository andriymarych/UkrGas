package com.gas.app.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String  name;

    @Column(name = "surname")
    private String  surname;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonManagedReference
    private Auth auth;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Collection<GasAccount> gasAccounts;

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
}
