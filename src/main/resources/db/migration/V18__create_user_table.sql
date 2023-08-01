CREATE TABLE ukr_gas.user
(
    id            bigint NOT NULL,
    first_name    character varying(40),
    last_name     character varying(40),
    email         character varying(40),
    password      character varying(250),
    role          character varying(20),
    creation_date date DEFAULT now()
);


ALTER TABLE ukr_gas.user
    OWNER TO postgres;

CREATE SEQUENCE ukr_gas.user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ukr_gas.user_id_seq
    OWNER TO postgres;


ALTER TABLE ONLY ukr_gas.user
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);

ALTER SEQUENCE ukr_gas.user_id_seq OWNED BY ukr_gas."user".id;

ALTER TABLE ONLY ukr_gas.user
    ALTER COLUMN id SET DEFAULT nextval('ukr_gas.user_id_seq'::regclass);

COPY ukr_gas.user (id, first_name, last_name, email, password, role, creation_date) FROM stdin;
12	Андрій	Марич	example1@gmail.com	$2a$10$IQqqjjrx39S3ZyQ3ueJSrO.9/3U4zUIM4yHi2/YJ1Z1yi2PuPgy/W	USER	2023-07-08
14	Олена	Задорожна	andriumarich@gmail.com	$2a$10$Yah95Q9DnB6onffkjfWaZuK9.wYrDaZBWLweYMMLddXApyEtW0A1m	USER	2023-07-09
\.

SELECT pg_catalog.setval('ukr_gas.user_id_seq', 14, true);