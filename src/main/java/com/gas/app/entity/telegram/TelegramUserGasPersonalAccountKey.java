package com.gas.app.entity.telegram;

import com.gas.app.entity.personalAccount.PersonalGasAccount;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


@Embeddable
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TelegramUserGasPersonalAccountKey implements Serializable {

    @JoinColumn(name = "user_id")
    @ManyToOne
    private TelegramUser telegramUser;

    @JoinColumn(name = "personal_gas_account_id")
    @ManyToOne
    private PersonalGasAccount personalGasAccount;
}
