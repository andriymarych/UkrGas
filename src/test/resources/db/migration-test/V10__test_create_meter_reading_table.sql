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

