CREATE TABLE ukr_gas.feedback_category
(
    id       integer NOT NULL,
    category character varying(60)
);


ALTER TABLE ukr_gas.feedback_category
    OWNER TO postgres;

ALTER TABLE ONLY ukr_gas.feedback_category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);


COPY ukr_gas.feedback_category (id, category) FROM stdin;
1	Фінансові питання та розрахунки
2	Зміна постачальника
3	Тарифи та умови договору
\.
