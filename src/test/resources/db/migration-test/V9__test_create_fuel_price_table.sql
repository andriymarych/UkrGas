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
