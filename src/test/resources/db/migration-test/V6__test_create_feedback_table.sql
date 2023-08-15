CREATE TABLE ukr_gas.feedback
(
    id          bigint NOT NULL,
    status      character varying(50)    DEFAULT 'Open'::character varying,
    full_name   character varying(150),
    email       character varying(70),
    category_id integer,
    type_id     integer,
    content     character varying(2000),
    "timestamp" timestamp with time zone DEFAULT now()
);

ALTER TABLE ukr_gas.feedback
    OWNER TO postgres;

ALTER TABLE ONLY ukr_gas.feedback
    ADD CONSTRAINT feedback_pkey PRIMARY KEY (id);

CREATE SEQUENCE ukr_gas.feedback_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ukr_gas.feedback_id_seq
    OWNER TO postgres;

ALTER SEQUENCE ukr_gas.feedback_id_seq OWNED BY ukr_gas.feedback.id;

ALTER TABLE ONLY ukr_gas.feedback
    ALTER COLUMN id SET DEFAULT nextval('ukr_gas.feedback_id_seq'::regclass);
