package com.gas.app.entity.personalAccount;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;

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

    @Column(name = "accrued_payment")
    private Double accruedPayment;

    @Column(name = "paid_payment")
    private Double paidPayment;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "date", insertable = false)
    @Generated
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "personal_gas_account_id", referencedColumnName = "id")
    @JsonBackReference
    private PersonalGasAccount personalGasAccount;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tariff_id", referencedColumnName = "id")
    private Tariff tariff;

}
