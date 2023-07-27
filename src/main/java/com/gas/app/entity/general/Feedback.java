package com.gas.app.entity.general;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.sql.Timestamp;

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

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "content")
    private String content;

    @Column(name = "timestamp", insertable = false)
    @Generated(GenerationTime.INSERT)
    private Timestamp timestamp;

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
