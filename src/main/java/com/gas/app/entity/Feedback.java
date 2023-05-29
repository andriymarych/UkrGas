package com.gas.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "feedback")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Feedback {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "status")
    private String status;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "content")
    private String content;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "type_id")
    private Integer typeId;

    public Feedback(String fullName, String email, String content) {
        this.fullName = fullName;
        this.email = email;
        this.content = content;
    }
}
