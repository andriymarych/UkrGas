package com.gas.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;


import java.sql.Date;

@Entity
@Table(name = "account_tariff")
@Getter @Setter
@ToString
@NoArgsConstructor
public class
AccountTariff {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "accountTariff")
    @JsonBackReference
    private PersonalGasAccount personalGasAccount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tariff_id", referencedColumnName = "id")
    @JsonBackReference
    private Tariff tariff;



}
