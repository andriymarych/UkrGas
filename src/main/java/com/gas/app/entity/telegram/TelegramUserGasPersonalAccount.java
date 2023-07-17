package com.gas.app.entity.telegram;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "telegram_user_personal_gas_account")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TelegramUserGasPersonalAccount {

    @EmbeddedId
    private TelegramUserGasPersonalAccountKey key;

   /* @MapsId("telegramUser")
    @ManyToOne(cascade = CascadeType.REMOVE)
    private TelegramUser telegramUser;

    @MapsId("personalGasAccount")
    @ManyToOne
    private PersonalGasAccount personalGasAccount;*/

    @Column(name = "verified")
    private Boolean verified = false;

}
