ALTER TABLE ONLY ukr_gas.account_tariff
    ADD CONSTRAINT "FK_AccountTariff_TariffId" FOREIGN KEY (tariff_id) REFERENCES ukr_gas.tariff (id);

ALTER TABLE ONLY ukr_gas.calculation
    ADD CONSTRAINT "FK_Calculation_Personal_gas_account_id" FOREIGN KEY (personal_gas_account_id) REFERENCES ukr_gas.personal_gas_account (id) NOT VALID;

ALTER TABLE ONLY ukr_gas.calculation
    ADD CONSTRAINT "FK_Calculation_Personal_tariff_id" FOREIGN KEY (tariff_id) REFERENCES ukr_gas.tariff (id) NOT VALID;

ALTER TABLE ONLY ukr_gas.feedback
    ADD CONSTRAINT "FK_Feedback_CategoryId" FOREIGN KEY (category_id) REFERENCES ukr_gas.feedback_category (id) NOT VALID;

ALTER TABLE ONLY ukr_gas.feedback
    ADD CONSTRAINT "FK_Feedback_TypeId" FOREIGN KEY (type_id) REFERENCES ukr_gas.feedback_type (id) NOT VALID;

ALTER TABLE ONLY ukr_gas.personal_gas_account
    ADD CONSTRAINT "FK_GasAccount_AddressId" FOREIGN KEY (address_id) REFERENCES ukr_gas.address (id);

ALTER TABLE ONLY ukr_gas.personal_gas_account
    ADD CONSTRAINT "FK_GasAccount_PersonId" FOREIGN KEY (person_id) REFERENCES ukr_gas.person (id);

ALTER TABLE ONLY ukr_gas.personal_gas_account
    ADD CONSTRAINT "FK_GasAccount_TariffId" FOREIGN KEY (tariff_id) REFERENCES ukr_gas.account_tariff (id) NOT VALID;

ALTER TABLE ONLY ukr_gas.personal_gas_account
    ADD CONSTRAINT "FK_GasAccount_UserId" FOREIGN KEY (user_id) REFERENCES ukr_gas.user (id);

ALTER TABLE ONLY ukr_gas.meter_reading
    ADD CONSTRAINT "FK_MeterReading_Personal_gas_account_id" FOREIGN KEY (personal_gas_account_id) REFERENCES ukr_gas.personal_gas_account (id) NOT VALID;

ALTER TABLE ONLY ukr_gas.payment
    ADD CONSTRAINT "FK_Payment_Personal_gas_account_id" FOREIGN KEY (personal_gas_account_id) REFERENCES ukr_gas.personal_gas_account (id) NOT VALID;

ALTER TABLE ONLY ukr_gas.telegram_user_personal_gas_account
    ADD CONSTRAINT "FK_PersonalGasAccount" FOREIGN KEY (personal_gas_account_id) REFERENCES ukr_gas.personal_gas_account (id) ON DELETE CASCADE NOT VALID;

ALTER TABLE ONLY ukr_gas.telegram_user
    ADD CONSTRAINT "FK_TelegramUser_CurrentPersonalGasAccount" FOREIGN KEY (current_personal_gas_account_id) REFERENCES ukr_gas.personal_gas_account (id) NOT VALID;

ALTER TABLE ONLY ukr_gas.token
    ADD CONSTRAINT "FK_Token_UserId" FOREIGN KEY (user_id) REFERENCES ukr_gas."user" (id) NOT VALID;

ALTER TABLE ONLY ukr_gas.telegram_user_personal_gas_account
    ADD CONSTRAINT "FK_UserId" FOREIGN KEY (user_id) REFERENCES ukr_gas.telegram_user (id) ON DELETE CASCADE NOT VALID;

