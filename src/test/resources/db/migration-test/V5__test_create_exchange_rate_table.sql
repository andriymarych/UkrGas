CREATE TABLE ukr_gas.exchange_rate
(
    id            bigint               NOT NULL,
    currency_from character varying(3) DEFAULT 'USD'::character varying,
    currency_to   character varying(3) NOT NULL,
    exchange_rate numeric(10, 4)       NOT NULL,
    date          date                 DEFAULT now()
);

ALTER TABLE ONLY ukr_gas.exchange_rate
    ADD CONSTRAINT currency_rate_pkey PRIMARY KEY (id);

ALTER TABLE ukr_gas.exchange_rate
    OWNER TO postgres;

CREATE SEQUENCE ukr_gas.exchange_rate_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ukr_gas.exchange_rate_id_seq
    OWNER TO postgres;

ALTER SEQUENCE ukr_gas.exchange_rate_id_seq OWNED BY ukr_gas.exchange_rate.id;

ALTER TABLE ONLY ukr_gas.exchange_rate
    ALTER COLUMN id SET DEFAULT nextval('ukr_gas.exchange_rate_id_seq'::regclass);
