package com.gas.app.entity.telegram;

import com.gas.app.entity.personalAccount.PersonalGasAccount;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Embeddable
@Data
@NoArgsConstructor
public class TelegramUserGasPersonalAccountKey implements Serializable {

    @JoinColumn(name = "user_id")
    @ManyToOne
    private TelegramUser telegramUser;

    @JoinColumn(name = "personal_gas_account_id")
    @ManyToOne
    private PersonalGasAccount personalGasAccount;

}
