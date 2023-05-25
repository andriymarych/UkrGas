package com.gas.app.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Table(name = "feedback_category")
@Data
@NoArgsConstructor
public class FeedbackCategory {
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "category")
    private String category;

    @OneToMany(mappedBy = "categoryId", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Collection<Feedback> feedbacks;

}
