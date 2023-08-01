CREATE TABLE ukr_gas.account_tariff
(
    id         bigint NOT NULL,
    discount   double precision,
    end_date   date,
    start_date date,
    tariff_id  bigint
);


ALTER TABLE ukr_gas.account_tariff
    OWNER TO postgres;

ALTER TABLE ONLY ukr_gas.account_tariff
    ADD CONSTRAINT account_tariff_pkey PRIMARY KEY (id);

CREATE SEQUENCE ukr_gas.account_tariff_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ukr_gas.account_tariff_id_seq
    OWNER TO postgres;

ALTER SEQUENCE ukr_gas.account_tariff_id_seq OWNED BY ukr_gas.account_tariff.id;

ALTER TABLE ONLY ukr_gas.account_tariff
    ALTER COLUMN id SET DEFAULT nextval('ukr_gas.account_tariff_id_seq'::regclass);

COPY ukr_gas.account_tariff (id, discount, end_date, start_date, tariff_id) FROM stdin;
1	0	2024-01-01	2023-01-01	1
2	0	2024-01-01	2023-01-01	2
\.