CREATE TABLE ukr_gas.tariff
(
    id    bigint NOT NULL,
    plan  character varying(255),
    price double precision
);


ALTER TABLE ukr_gas.tariff
    OWNER TO postgres;

ALTER TABLE ONLY ukr_gas.tariff
    ADD CONSTRAINT tariff_pkey PRIMARY KEY (id);


CREATE SEQUENCE ukr_gas.tariff_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ukr_gas.tariff_id_seq
    OWNER TO postgres;

ALTER SEQUENCE ukr_gas.tariff_id_seq OWNED BY ukr_gas.tariff.id;

ALTER TABLE ONLY ukr_gas.tariff
    ALTER COLUMN id SET DEFAULT nextval('ukr_gas.tariff_id_seq'::regclass);

