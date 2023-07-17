package com.gas.app.entity.telegram;


import com.gas.app.entity.personalAccount.PersonalGasAccount;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "telegram_user")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TelegramUser {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "verified")
    private Boolean verified;

    @ManyToOne
    @JoinColumn(name = "current_personal_gas_account_id",referencedColumnName = "id")
    private PersonalGasAccount currentPersonalGasAccount;

    @Column(name = "last_bot_state")
    @Enumerated(EnumType.STRING)
    private BotState botState = BotState.REGISTRATION;
}
