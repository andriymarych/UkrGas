CREATE TABLE ukr_gas.personal_gas_account
(
    id               bigint NOT NULL,
    person_id        bigint,
    user_id          bigint,
    balance          numeric(8, 2),
    iec_number       character varying(255),
    gas_meter_number bigint,
    address_id       bigint,
    account_number   character varying(20),
    tariff_id        bigint
);


ALTER TABLE ukr_gas.personal_gas_account
    OWNER TO postgres;

ALTER TABLE ONLY ukr_gas.personal_gas_account
    ADD CONSTRAINT gas_account_pkey PRIMARY KEY (id);

ALTER TABLE ONLY ukr_gas.personal_gas_account
    ADD CONSTRAINT "UQ_PersonalGasAccount_AccountNumber" UNIQUE (account_number);

ALTER TABLE ONLY ukr_gas.personal_gas_account
    ADD CONSTRAINT "UQ_PersonalGasAccount_GasMeterNumber" UNIQUE (gas_meter_number);

CREATE SEQUENCE ukr_gas.personal_gas_account_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ukr_gas.personal_gas_account_id_seq
    OWNER TO postgres;

ALTER SEQUENCE ukr_gas.personal_gas_account_id_seq OWNED BY ukr_gas.personal_gas_account.id;

ALTER TABLE ONLY ukr_gas.personal_gas_account
    ALTER COLUMN id SET DEFAULT nextval('ukr_gas.personal_gas_account_id_seq'::regclass);
