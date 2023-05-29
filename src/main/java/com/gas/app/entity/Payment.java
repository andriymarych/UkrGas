package com.gas.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name="payment")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Payment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;


    @Column(name = "amount_paid")
    private Double amountPaid;

    @Column(name = "date")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "gas_account_id", referencedColumnName = "id")
    @JsonBackReference
    private PersonalGasAccount personalGasAccount;

}
