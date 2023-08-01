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

COPY ukr_gas.person (id, first_name, last_name) FROM stdin;
1001	Андрій	Марич
1002	Олена	Задорожна
\.