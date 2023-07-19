package com.gas.app.entity.personalAccount;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="tariff")
@Getter @Setter
@ToString
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
