package com.gas.app.entity.telegram;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "telegram_user_personal_gas_account")
@Data
@NoArgsConstructor
public class TelegramUserGasPersonalAccount {

    @EmbeddedId
    private TelegramUserGasPersonalAccountKey key;

    @Column(name = "verified")
    private Boolean verified = false;

    public TelegramUserGasPersonalAccount(TelegramUserGasPersonalAccountKey key) {
        this.key = key;
    }

}
