package com.gas.app.entity.personalAccount;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.sql.Date;

@Entity
@Table(name="meter_reading")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MeterReading {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "meter_reading")
    private Double meterReading;

    @Column(name = "date", insertable = false)
    @Generated(GenerationTime.INSERT)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_gas_account_id", referencedColumnName = "id")
    private PersonalGasAccount personalGasAccount;

    public MeterReading(Double meterReading) {
        this.meterReading = meterReading;
    }
}
