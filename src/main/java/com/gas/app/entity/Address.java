package com.gas.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
public class Address {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "region")
    private String region;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "house_number")
    private Integer houseNumber;

    @Column(name = "apartment_number")
    private Integer apartmentNumber;

    @OneToOne(mappedBy = "addressId")
    @JsonBackReference
    private GasAccount gasAccount;


}
