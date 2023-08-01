CREATE TABLE ukr_gas.calculation
(
    id                      bigint NOT NULL,
    personal_gas_account_id bigint,
    amount_consumed         double precision,
    accrued_payment         double precision,
    paid_payment            double precision,
    balance                 double precision,
    tariff_id               bigint,
    date                    date DEFAULT now()
);


ALTER TABLE ukr_gas.calculation
    OWNER TO postgres;

ALTER TABLE ONLY ukr_gas.calculation
    ADD CONSTRAINT calculation_pkey PRIMARY KEY (id);

CREATE SEQUENCE ukr_gas.calculation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ukr_gas.calculation_id_seq
    OWNER TO postgres;

ALTER SEQUENCE ukr_gas.calculation_id_seq OWNED BY ukr_gas.calculation.id;

ALTER TABLE ONLY ukr_gas.calculation
    ALTER COLUMN id SET DEFAULT nextval('ukr_gas.calculation_id_seq'::regclass);


COPY ukr_gas.calculation (id, personal_gas_account_id, amount_consumed, accrued_payment, paid_payment, balance,
                          tariff_id, date) FROM stdin;
1	1	65	510	610	0	1	2023-05-15
2	1	100	795.689	695.23	-140	1	2023-04-15
\.

SELECT pg_catalog.setval('ukr_gas.calculation_id_seq', 3, true);