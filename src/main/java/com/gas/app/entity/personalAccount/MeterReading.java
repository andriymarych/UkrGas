package com.gas.app.entity.personalAccount;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;

import java.util.Date;

@Entity
@Table(name="meter_reading")
@Data
@NoArgsConstructor
public class MeterReading {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "meter_reading")
    private Double meterReading;

    @Column(name = "date", insertable = false)
    @Generated
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "personal_gas_account_id", referencedColumnName = "id")
    private PersonalGasAccount personalGasAccount;

    public MeterReading(Double meterReading) {
        this.meterReading = meterReading;
    }
}
