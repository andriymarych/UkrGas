CREATE TABLE ukr_gas.person
(
    id         bigint NOT NULL,
    first_name character varying(70),
    last_name  character varying(70)
);


ALTER TABLE ukr_gas.person
    OWNER TO postgres;

ALTER TABLE ONLY ukr_gas.person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);

CREATE SEQUENCE ukr_gas.person_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ukr_gas.person_id_seq
    OWNER TO postgres;

ALTER SEQUENCE ukr_gas.person_id_seq OWNED BY ukr_gas.person.id;

ALTER TABLE ONLY ukr_gas.person
    ALTER COLUMN id SET DEFAULT nextval('ukr_gas.person_id_seq'::regclass);