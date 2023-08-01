CREATE TABLE ukr_gas.address
(
    id               bigint NOT NULL,
    apartment_number integer,
    city             character varying(255),
    house_number     integer,
    region           character varying(255),
    street           character varying(255)
);


ALTER TABLE ukr_gas.address
    OWNER TO postgres;

ALTER TABLE ONLY ukr_gas.address
    ADD CONSTRAINT address_pkey PRIMARY KEY (id);

CREATE SEQUENCE ukr_gas.address_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ukr_gas.address_id_seq
    OWNER TO postgres;


ALTER SEQUENCE ukr_gas.address_id_seq OWNED BY ukr_gas.address.id;

ALTER TABLE ONLY ukr_gas.address
    ALTER COLUMN id SET DEFAULT nextval('ukr_gas.address_id_seq'::regclass);

COPY ukr_gas.address (id, apartment_number, city, house_number, region, street) FROM stdin;
5	\N	Львів	124	Львівська область	Зелена
6	\N	Івано-Франківськ	30	Івано-Франківська область	Вовчинецька
\.

SELECT pg_catalog.setval('ukr_gas.address_id_seq', 7, false);

