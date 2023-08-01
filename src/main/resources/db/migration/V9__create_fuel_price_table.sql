CREATE TABLE ukr_gas.fuel_price
(
    id       bigint                NOT NULL,
    type     character varying(30) NOT NULL,
    price    numeric(10, 3)        NOT NULL,
    currency character varying(3),
    country  character varying(50),
    date     date DEFAULT now()
);


ALTER TABLE ukr_gas.fuel_price
    OWNER TO postgres;

ALTER TABLE ONLY ukr_gas.fuel_price
    ADD CONSTRAINT fuel_price_pkey PRIMARY KEY (id);


CREATE SEQUENCE ukr_gas.fuel_price_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ukr_gas.fuel_price_id_seq
    OWNER TO postgres;

ALTER SEQUENCE ukr_gas.fuel_price_id_seq OWNED BY ukr_gas.fuel_price.id;

ALTER TABLE ONLY ukr_gas.fuel_price
    ALTER COLUMN id SET DEFAULT nextval('ukr_gas.fuel_price_id_seq'::regclass);

COPY ukr_gas.fuel_price (id, type, price, currency, country, date) FROM stdin;
19	LPG	0.660	USD	Ukraine	2023-07-30
21	GASOLINE	1.329	USD	Ukraine	2023-07-30
20	DIESEL	1.351	USD	Ukraine	2023-07-30
22	LPG	0.661	USD	Ukraine	2023-07-31
23	DIESEL	1.352	USD	Ukraine	2023-07-31
24	GASOLINE	1.331	USD	Ukraine	2023-07-31
17	DIESEL	1.363	USD	Ukraine	2023-07-17
16	LPG	0.651	USD	Ukraine	2023-07-17
18	GASOLINE	1.318	USD	Ukraine	2023-07-17
\.

SELECT pg_catalog.setval('ukr_gas.fuel_price_id_seq', 24, true);