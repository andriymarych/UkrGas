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
    ALTER COLUMN id SET DEFAULT 'ukr_gas.personal_gas_account_id_seq'::regclass;


COPY ukr_gas.personal_gas_account (id, person_id, user_id, balance, iec_number, gas_meter_number, address_id,
                                   account_number, tariff_id) FROM stdin;
2	1002	\N	24.00	23IA16A509084853	975032	6	07753146	2
1	1001	\N	100.65	56XM16A507384234	342014	5	03234124	1
3	1002	14	1010.12	98XC1609684888A3	869321	6	04682124	2
\.

SELECT pg_catalog.setval('ukr_gas.personal_gas_account_id_seq', 2, true);