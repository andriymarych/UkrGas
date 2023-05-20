package com.gas.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;


import java.sql.Date;

@Entity
@Table(name = "account_tariff")
@Data
@NoArgsConstructor
public class AccountTariff {

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

    @ManyToOne
    @JoinColumn(name = "tariff_id", referencedColumnName = "id")
    @JsonBackReference
    private Tariff tariff;

    @ManyToOne
    @JoinColumn(name = "gas_account_id", referencedColumnName = "id")
    @JsonBackReference
    private GasAccount gasAccount;


}
