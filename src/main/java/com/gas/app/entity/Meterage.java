package com.gas.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name="meterage")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Meterage {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "meter_readings")
    private Long meterReadings;

    @Column(name = "date")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gas_account_id", referencedColumnName = "id")
    @JsonBackReference
    private PersonalGasAccount personalGasAccount;

}
