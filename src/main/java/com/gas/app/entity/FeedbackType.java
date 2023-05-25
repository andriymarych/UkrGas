package com.gas.app.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Table(name = "feedback_type")
@Data
@NoArgsConstructor
public class FeedbackType {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "typeId", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Collection<Feedback> feedbacks;



}
