package com.gas.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Entity
@Table(name = "personal_gas_account")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PersonalGasAccount {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "IEC_number")
    private String iecNumber;

    @Column(name = "gas_meter_number")
    private Long gasMeterNumber;

    @Column(name = "account_number")
    private String accountNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    @JsonBackReference
    private Person person;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn( name = "tariff_id", referencedColumnName = "id")
    @JsonManagedReference
    private AccountTariff accountTariff;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn( name = "address_id", referencedColumnName = "id")
    @JsonManagedReference
    private Address address;

   /* @OneToMany(mappedBy = "personalGasAccount", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Collection<Calculation> calculations;

    @OneToMany(mappedBy = "personalGasAccount", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Collection<MeterReading> meterages;

    @OneToMany(mappedBy = "personalGasAccount", fetch = FetchType.LAZY )
    @JsonManagedReference
    private Collection<Payment> payments;*/

}
