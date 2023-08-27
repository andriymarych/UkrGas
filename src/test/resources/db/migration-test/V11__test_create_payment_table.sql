CREATE TABLE ukr_gas.payment
(
    id                      bigint NOT NULL,
    amount_paid             numeric(8, 2),
    date                    timestamp with time zone DEFAULT now(),
    personal_gas_account_id bigint
);


ALTER TABLE ukr_gas.payment
    OWNER TO postgres;

ALTER TABLE ONLY ukr_gas.payment
    ADD CONSTRAINT payment_pkey PRIMARY KEY (id);

CREATE SEQUENCE ukr_gas.payment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ukr_gas.payment_id_seq
    OWNER TO postgres;

ALTER SEQUENCE ukr_gas.payment_id_seq OWNED BY ukr_gas.payment.id;

ALTER TABLE ONLY ukr_gas.payment
    ALTER COLUMN id SET DEFAULT nextval('ukr_gas.payment_id_seq'::regclass);

