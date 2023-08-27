CREATE TABLE ukr_gas.telegram_user_personal_gas_account
(
    user_id                 bigint NOT NULL,
    personal_gas_account_id bigint NOT NULL,
    verified                boolean DEFAULT false
);

ALTER TABLE ukr_gas.telegram_user_personal_gas_account
    OWNER TO postgres;

ALTER TABLE ONLY ukr_gas.telegram_user_personal_gas_account
    ADD CONSTRAINT telegram_user_personal_gas_account_pkey PRIMARY KEY (user_id, personal_gas_account_id);
