package com.gas.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.sql.Timestamp;

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

    @Column(name = "date", insertable = false)
    @Generated(GenerationTime.INSERT)
    private Timestamp timestamp;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "personal_gas_account_id", referencedColumnName = "id")
    @JsonBackReference
    private PersonalGasAccount personalGasAccount;

    public Payment(Double amountPaid) {
        this.amountPaid = amountPaid;
    }
}
