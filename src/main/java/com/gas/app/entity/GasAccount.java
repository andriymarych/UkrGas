package com.gas.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Table(name = "gas_account")
@Data
@NoArgsConstructor
public class GasAccount {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "balance")
    private Long balance;

    @Column(name = "IEC_number")
    private String iecNumber;

    @OneToMany(mappedBy = "gasAccount", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Collection<Calculation> calculations;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "gasAccount", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Collection<AccountTariff> accountTariff;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn( name = "address_id", referencedColumnName = "id")
    @JsonManagedReference
    private Address addressId;

    @OneToMany(mappedBy = "gasAccount", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Collection<Meterage> meterages;

    @OneToMany(mappedBy = "gasAccount", fetch = FetchType.LAZY )
    @JsonManagedReference
    private Collection<Payment> payments;


}
