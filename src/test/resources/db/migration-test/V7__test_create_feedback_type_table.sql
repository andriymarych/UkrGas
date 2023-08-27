CREATE TABLE ukr_gas.feedback_type
(
    id   integer NOT NULL,
    type character varying(60)
);

ALTER TABLE ukr_gas.feedback_type
    OWNER TO postgres;

ALTER TABLE ONLY ukr_gas.feedback_type
    ADD CONSTRAINT feedback_type_pkey PRIMARY KEY (id);

