CREATE SEQUENCE ukr_gas.token_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ukr_gas.token_id_seq
    OWNER TO postgres;

CREATE TABLE ukr_gas.token
(
    id      bigint DEFAULT nextval('ukr_gas.token_id_seq'::regclass) NOT NULL,
    token   character varying(250),
    type    character varying(30),
    expired boolean,
    revoked boolean,
    user_id bigint
);

ALTER TABLE ukr_gas.token
    OWNER TO postgres;

ALTER TABLE ONLY ukr_gas.token
    ADD CONSTRAINT token_pkey PRIMARY KEY (id);

