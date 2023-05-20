package com.gas.app.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

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

    @OneToMany(mappedBy = "tariff", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Collection<AccountTariff> accountTariffs;

}
