CREATE TABLE ukr_gas.meter_reading
(
    id                      bigint NOT NULL,
    date                    date DEFAULT now(),
    meter_reading           numeric(10, 2),
    personal_gas_account_id bigint
);


ALTER TABLE ukr_gas.meter_reading
    OWNER TO postgres;


ALTER TABLE ONLY ukr_gas.meter_reading
    ADD CONSTRAINT meterage_pkey PRIMARY KEY (id);

CREATE SEQUENCE ukr_gas.meter_reading_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ukr_gas.meter_reading_id_seq
    OWNER TO postgres;

ALTER SEQUENCE ukr_gas.meter_reading_id_seq OWNED BY ukr_gas.meter_reading.id;

ALTER TABLE ONLY ukr_gas.meter_reading
    ALTER COLUMN id SET DEFAULT nextval('ukr_gas.meter_reading_id_seq'::regclass);

COPY ukr_gas.meter_reading (id, date, meter_reading, personal_gas_account_id) FROM stdin;
2	2023-02-03	31820.00	1
3	2023-03-04	32105.00	1
1	2023-01-01	31456.00	1
23	2023-06-03	32301.00	2
34	2023-07-02	32405.00	2
35	2023-05-05	32115.00	3
36	2023-06-01	32225.00	3
22	2023-05-03	32405.00	1
7	2023-04-05	32301.00	1
25	2023-06-02	32504.00	1
41	2023-07-27	32620.00	1
\.

SELECT pg_catalog.setval('ukr_gas.meter_reading_id_seq', 41, true);