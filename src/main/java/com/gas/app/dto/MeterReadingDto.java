package com.gas.app.dto;

import com.gas.app.entity.PersonalGasAccount;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.GenerationTime;

import java.sql.Date;

public record MeterReadingDto(Long id, Double meterReading, Date date, Long personalGasAccountId) {
}