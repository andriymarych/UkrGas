package com.gas.app.entity.personalAccount;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tariff")
@Data
@NoArgsConstructor
public class Tariff {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "plan")
    private String plan;

    @Column(name = "price")
    private Double price;

}
