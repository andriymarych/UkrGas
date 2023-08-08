package com.gas.app.entity.personalAccount;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gas.app.entity.security.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "personal_gas_account")
@Data
@NoArgsConstructor
public class PersonalGasAccount {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "IEC_number")
    private String iecNumber;

    @Column(name = "gas_meter_number")
    private Long gasMeterNumber;

    @Column(name = "account_number")
    private String accountNumber;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn( name = "tariff_id", referencedColumnName = "id")
    private AccountTariff accountTariff;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn( name = "address_id", referencedColumnName = "id")
    private Address address;

}
