package com.gas.app.entity.personalAccount;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name="payment")
@Data
@NoArgsConstructor
public class Payment {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "amount_paid")
    private Double amountPaid;

    @Column(name = "date")
    private Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "personal_gas_account_id", referencedColumnName = "id")
    private PersonalGasAccount personalGasAccount;

    public Payment(Double amountPaid) {
        this.amountPaid = amountPaid;
    }
}
