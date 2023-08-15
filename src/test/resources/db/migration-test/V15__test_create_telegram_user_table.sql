CREATE TABLE ukr_gas.telegram_user
(
    id                              bigint NOT NULL,
    username                        character varying(40),
    chat_id                         bigint,
    verified                        boolean DEFAULT false,
    current_personal_gas_account_id bigint,
    last_bot_state                  character varying(50)
);

ALTER TABLE ukr_gas.telegram_user
    OWNER TO postgres;

ALTER TABLE ONLY ukr_gas.telegram_user
    ADD CONSTRAINT telegram_user_pkey PRIMARY KEY (id);

ALTER TABLE ONLY ukr_gas.telegram_user
    ADD CONSTRAINT "UQ_TelegramUser_ChatId" UNIQUE (chat_id);

ALTER TABLE ONLY ukr_gas.telegram_user
    ADD CONSTRAINT "UQ_TelegramUser_Username" UNIQUE (username);

CREATE SEQUENCE ukr_gas.telegram_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ukr_gas.telegram_user_id_seq
    OWNER TO postgres;

ALTER SEQUENCE ukr_gas.telegram_user_id_seq OWNED BY ukr_gas.telegram_user.id;

ALTER TABLE ONLY ukr_gas.telegram_user
    ALTER COLUMN id SET DEFAULT nextval('ukr_gas.telegram_user_id_seq'::regclass);
