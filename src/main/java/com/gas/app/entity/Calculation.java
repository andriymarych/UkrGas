package com.gas.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "calculation")
@Data
@NoArgsConstructor
public class Calculation {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "amount_consumed")
    private Double amountConsumed;

    @Column(name = "payment_amount")
    private Double paymentAmount;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "gas_account_id", referencedColumnName = "id")
    @JsonBackReference
    private GasAccount gasAccount;

}
