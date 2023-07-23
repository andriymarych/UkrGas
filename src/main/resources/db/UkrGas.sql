--
-- PostgreSQL database dump
--

-- Dumped from database version 14.5
-- Dumped by pg_dump version 14.4

-- Started on 2023-07-22 01:44:27 EEST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 17179)
-- Name: ukr_gas; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA ukr_gas;


ALTER SCHEMA ukr_gas OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 19753)
-- Name: auth_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.auth_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.auth_id_seq OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 19754)
-- Name: user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_id_seq OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 211 (class 1259 OID 19561)
-- Name: account_tariff; Type: TABLE; Schema: ukr_gas; Owner: postgres
--

CREATE TABLE ukr_gas.account_tariff (
    id bigint NOT NULL,
    discount double precision,
    end_date date,
    start_date date,
    tariff_id bigint
);


ALTER TABLE ukr_gas.account_tariff OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 19560)
-- Name: account_tariff_id_seq; Type: SEQUENCE; Schema: ukr_gas; Owner: postgres
--

CREATE SEQUENCE ukr_gas.account_tariff_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ukr_gas.account_tariff_id_seq OWNER TO postgres;

--
-- TOC entry 3779 (class 0 OID 0)
-- Dependencies: 210
-- Name: account_tariff_id_seq; Type: SEQUENCE OWNED BY; Schema: ukr_gas; Owner: postgres
--

ALTER SEQUENCE ukr_gas.account_tariff_id_seq OWNED BY ukr_gas.account_tariff.id;


--
-- TOC entry 213 (class 1259 OID 19568)
-- Name: address; Type: TABLE; Schema: ukr_gas; Owner: postgres
--

CREATE TABLE ukr_gas.address (
    id bigint NOT NULL,
    apartment_number integer,
    city character varying(255),
    house_number integer,
    region character varying(255),
    street character varying(255)
);


ALTER TABLE ukr_gas.address OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 19567)
-- Name: address_id_seq; Type: SEQUENCE; Schema: ukr_gas; Owner: postgres
--

CREATE SEQUENCE ukr_gas.address_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ukr_gas.address_id_seq OWNER TO postgres;

--
-- TOC entry 3780 (class 0 OID 0)
-- Dependencies: 212
-- Name: address_id_seq; Type: SEQUENCE OWNED BY; Schema: ukr_gas; Owner: postgres
--

ALTER SEQUENCE ukr_gas.address_id_seq OWNED BY ukr_gas.address.id;


--
-- TOC entry 214 (class 1259 OID 19577)
-- Name: auth; Type: TABLE; Schema: ukr_gas; Owner: postgres
--

CREATE TABLE ukr_gas.auth (
    id bigint NOT NULL,
    email character varying(255),
    password character varying(255)
);


ALTER TABLE ukr_gas.auth OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 19757)
-- Name: auth_id_seq; Type: SEQUENCE; Schema: ukr_gas; Owner: postgres
--

CREATE SEQUENCE ukr_gas.auth_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ukr_gas.auth_id_seq OWNER TO postgres;

--
-- TOC entry 3781 (class 0 OID 0)
-- Dependencies: 234
-- Name: auth_id_seq; Type: SEQUENCE OWNED BY; Schema: ukr_gas; Owner: postgres
--

ALTER SEQUENCE ukr_gas.auth_id_seq OWNED BY ukr_gas.auth.id;


--
-- TOC entry 216 (class 1259 OID 19586)
-- Name: calculation; Type: TABLE; Schema: ukr_gas; Owner: postgres
--

CREATE TABLE ukr_gas.calculation (
    id bigint NOT NULL,
    personal_gas_account_id bigint,
    amount_consumed double precision,
    accrued_payment double precision,
    paid_payment double precision,
    balance double precision,
    tariff_id bigint,
    date date DEFAULT now()
);


ALTER TABLE ukr_gas.calculation OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 19585)
-- Name: calculation_id_seq; Type: SEQUENCE; Schema: ukr_gas; Owner: postgres
--

CREATE SEQUENCE ukr_gas.calculation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ukr_gas.calculation_id_seq OWNER TO postgres;

--
-- TOC entry 3782 (class 0 OID 0)
-- Dependencies: 215
-- Name: calculation_id_seq; Type: SEQUENCE OWNED BY; Schema: ukr_gas; Owner: postgres
--

ALTER SEQUENCE ukr_gas.calculation_id_seq OWNED BY ukr_gas.calculation.id;


--
-- TOC entry 236 (class 1259 OID 19902)
-- Name: exchange_rate; Type: TABLE; Schema: ukr_gas; Owner: postgres
--

CREATE TABLE ukr_gas.exchange_rate (
    id bigint NOT NULL,
    currency_from character varying(3) DEFAULT 'USD'::character varying,
    currency_to character varying(3) NOT NULL,
    exchange_rate numeric(10,4) NOT NULL,
    date date DEFAULT now()
);


ALTER TABLE ukr_gas.exchange_rate OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 19911)
-- Name: currency_rate_id_seq; Type: SEQUENCE; Schema: ukr_gas; Owner: postgres
--

CREATE SEQUENCE ukr_gas.currency_rate_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ukr_gas.currency_rate_id_seq OWNER TO postgres;

--
-- TOC entry 3783 (class 0 OID 0)
-- Dependencies: 237
-- Name: currency_rate_id_seq; Type: SEQUENCE OWNED BY; Schema: ukr_gas; Owner: postgres
--

ALTER SEQUENCE ukr_gas.currency_rate_id_seq OWNED BY ukr_gas.exchange_rate.id;


--
-- TOC entry 224 (class 1259 OID 19677)
-- Name: feedback; Type: TABLE; Schema: ukr_gas; Owner: postgres
--

CREATE TABLE ukr_gas.feedback (
    id bigint NOT NULL,
    status character varying(50) DEFAULT 'Open'::character varying,
    full_name character varying(150),
    email character varying(70),
    category_id integer,
    type_id integer,
    content character varying(2000),
    "timestamp" timestamp with time zone DEFAULT now()
);


ALTER TABLE ukr_gas.feedback OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 19684)
-- Name: feedback_category; Type: TABLE; Schema: ukr_gas; Owner: postgres
--

CREATE TABLE ukr_gas.feedback_category (
    id integer NOT NULL,
    category character varying(60)
);


ALTER TABLE ukr_gas.feedback_category OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 19705)
-- Name: feedback_id_seq; Type: SEQUENCE; Schema: ukr_gas; Owner: postgres
--

CREATE SEQUENCE ukr_gas.feedback_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ukr_gas.feedback_id_seq OWNER TO postgres;

--
-- TOC entry 3784 (class 0 OID 0)
-- Dependencies: 227
-- Name: feedback_id_seq; Type: SEQUENCE OWNED BY; Schema: ukr_gas; Owner: postgres
--

ALTER SEQUENCE ukr_gas.feedback_id_seq OWNED BY ukr_gas.feedback.id;


--
-- TOC entry 226 (class 1259 OID 19689)
-- Name: feedback_type; Type: TABLE; Schema: ukr_gas; Owner: postgres
--

CREATE TABLE ukr_gas.feedback_type (
    id integer NOT NULL,
    type character varying(60)
);


ALTER TABLE ukr_gas.feedback_type OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 19890)
-- Name: fuel_price; Type: TABLE; Schema: ukr_gas; Owner: postgres
--

CREATE TABLE ukr_gas.fuel_price (
    id bigint NOT NULL,
    type character varying(30) NOT NULL,
    price numeric(10,3) NOT NULL,
    currency character varying(3),
    country character varying(50),
    date date DEFAULT now()
);


ALTER TABLE ukr_gas.fuel_price OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 19912)
-- Name: fuel_price_id_seq; Type: SEQUENCE; Schema: ukr_gas; Owner: postgres
--

CREATE SEQUENCE ukr_gas.fuel_price_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ukr_gas.fuel_price_id_seq OWNER TO postgres;

--
-- TOC entry 3785 (class 0 OID 0)
-- Dependencies: 238
-- Name: fuel_price_id_seq; Type: SEQUENCE OWNED BY; Schema: ukr_gas; Owner: postgres
--

ALTER SEQUENCE ukr_gas.fuel_price_id_seq OWNED BY ukr_gas.fuel_price.id;


--
-- TOC entry 229 (class 1259 OID 19720)
-- Name: personal_gas_account; Type: TABLE; Schema: ukr_gas; Owner: postgres
--

CREATE TABLE ukr_gas.personal_gas_account (
    id bigint NOT NULL,
    person_id bigint,
    user_id bigint,
    balance numeric(8,2),
    iec_number character varying(255),
    gas_meter_number bigint,
    address_id bigint,
    account_number character varying(20),
    tariff_id bigint
);


ALTER TABLE ukr_gas.personal_gas_account OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 19741)
-- Name: gas_account_id_seq; Type: SEQUENCE; Schema: ukr_gas; Owner: postgres
--

CREATE SEQUENCE ukr_gas.gas_account_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ukr_gas.gas_account_id_seq OWNER TO postgres;

--
-- TOC entry 3786 (class 0 OID 0)
-- Dependencies: 230
-- Name: gas_account_id_seq; Type: SEQUENCE OWNED BY; Schema: ukr_gas; Owner: postgres
--

ALTER SEQUENCE ukr_gas.gas_account_id_seq OWNED BY ukr_gas.personal_gas_account.id;


--
-- TOC entry 218 (class 1259 OID 19600)
-- Name: meter_reading; Type: TABLE; Schema: ukr_gas; Owner: postgres
--

CREATE TABLE ukr_gas.meter_reading (
    id bigint NOT NULL,
    date date DEFAULT now(),
    meter_reading numeric(10,2),
    personal_gas_account_id bigint
);


ALTER TABLE ukr_gas.meter_reading OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 19599)
-- Name: meter_reading_id_seq; Type: SEQUENCE; Schema: ukr_gas; Owner: postgres
--

CREATE SEQUENCE ukr_gas.meter_reading_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ukr_gas.meter_reading_id_seq OWNER TO postgres;

--
-- TOC entry 3787 (class 0 OID 0)
-- Dependencies: 217
-- Name: meter_reading_id_seq; Type: SEQUENCE OWNED BY; Schema: ukr_gas; Owner: postgres
--

ALTER SEQUENCE ukr_gas.meter_reading_id_seq OWNED BY ukr_gas.meter_reading.id;


--
-- TOC entry 220 (class 1259 OID 19607)
-- Name: payment; Type: TABLE; Schema: ukr_gas; Owner: postgres
--

CREATE TABLE ukr_gas.payment (
    id bigint NOT NULL,
    amount_paid numeric(8,2),
    date timestamp with time zone DEFAULT now(),
    personal_gas_account_id bigint
);


ALTER TABLE ukr_gas.payment OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 19606)
-- Name: payment_id_seq; Type: SEQUENCE; Schema: ukr_gas; Owner: postgres
--

CREATE SEQUENCE ukr_gas.payment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ukr_gas.payment_id_seq OWNER TO postgres;

--
-- TOC entry 3788 (class 0 OID 0)
-- Dependencies: 219
-- Name: payment_id_seq; Type: SEQUENCE OWNED BY; Schema: ukr_gas; Owner: postgres
--

ALTER SEQUENCE ukr_gas.payment_id_seq OWNED BY ukr_gas.payment.id;


--
-- TOC entry 228 (class 1259 OID 19707)
-- Name: person; Type: TABLE; Schema: ukr_gas; Owner: postgres
--

CREATE TABLE ukr_gas.person (
    id bigint NOT NULL,
    first_name character varying(70),
    last_name character varying(70)
);


ALTER TABLE ukr_gas.person OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 19614)
-- Name: tariff; Type: TABLE; Schema: ukr_gas; Owner: postgres
--

CREATE TABLE ukr_gas.tariff (
    id bigint NOT NULL,
    plan character varying(255),
    price double precision
);


ALTER TABLE ukr_gas.tariff OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 19613)
-- Name: tariff_id_seq; Type: SEQUENCE; Schema: ukr_gas; Owner: postgres
--

CREATE SEQUENCE ukr_gas.tariff_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ukr_gas.tariff_id_seq OWNER TO postgres;

--
-- TOC entry 3789 (class 0 OID 0)
-- Dependencies: 221
-- Name: tariff_id_seq; Type: SEQUENCE OWNED BY; Schema: ukr_gas; Owner: postgres
--

ALTER SEQUENCE ukr_gas.tariff_id_seq OWNED BY ukr_gas.tariff.id;


--
-- TOC entry 241 (class 1259 OID 20187)
-- Name: telegram_user; Type: TABLE; Schema: ukr_gas; Owner: postgres
--

CREATE TABLE ukr_gas.telegram_user (
    id bigint NOT NULL,
    username character varying(40),
    chat_id bigint,
    verified boolean DEFAULT false,
    current_personal_gas_account_id bigint,
    last_bot_state character varying(50)
);


ALTER TABLE ukr_gas.telegram_user OWNER TO postgres;

--
-- TOC entry 242 (class 1259 OID 20198)
-- Name: telegram_user_id_seq; Type: SEQUENCE; Schema: ukr_gas; Owner: postgres
--

CREATE SEQUENCE ukr_gas.telegram_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ukr_gas.telegram_user_id_seq OWNER TO postgres;

--
-- TOC entry 3790 (class 0 OID 0)
-- Dependencies: 242
-- Name: telegram_user_id_seq; Type: SEQUENCE OWNED BY; Schema: ukr_gas; Owner: postgres
--

ALTER SEQUENCE ukr_gas.telegram_user_id_seq OWNED BY ukr_gas.telegram_user.id;


--
-- TOC entry 243 (class 1259 OID 20213)
-- Name: telegram_user_personal_gas_account; Type: TABLE; Schema: ukr_gas; Owner: postgres
--

CREATE TABLE ukr_gas.telegram_user_personal_gas_account (
    user_id bigint NOT NULL,
    personal_gas_account_id bigint NOT NULL,
    verified boolean DEFAULT false
);


ALTER TABLE ukr_gas.telegram_user_personal_gas_account OWNER TO postgres;

--
-- TOC entry 240 (class 1259 OID 20175)
-- Name: token_id_seq; Type: SEQUENCE; Schema: ukr_gas; Owner: postgres
--

CREATE SEQUENCE ukr_gas.token_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ukr_gas.token_id_seq OWNER TO postgres;

--
-- TOC entry 239 (class 1259 OID 20170)
-- Name: token; Type: TABLE; Schema: ukr_gas; Owner: postgres
--

CREATE TABLE ukr_gas.token (
    id bigint DEFAULT nextval('ukr_gas.token_id_seq'::regclass) NOT NULL,
    token character varying(250),
    type character varying(30),
    expired boolean,
    revoked boolean,
    user_id bigint
);


ALTER TABLE ukr_gas.token OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 19621)
-- Name: user; Type: TABLE; Schema: ukr_gas; Owner: postgres
--

CREATE TABLE ukr_gas."user" (
    id bigint NOT NULL,
    first_name character varying(40),
    last_name character varying(40),
    email character varying(40),
    password character varying(250),
    role character varying(20),
    creation_date date DEFAULT now()
);


ALTER TABLE ukr_gas."user" OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 19756)
-- Name: user_id_seq; Type: SEQUENCE; Schema: ukr_gas; Owner: postgres
--

CREATE SEQUENCE ukr_gas.user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ukr_gas.user_id_seq OWNER TO postgres;

--
-- TOC entry 3791 (class 0 OID 0)
-- Dependencies: 233
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: ukr_gas; Owner: postgres
--

ALTER SEQUENCE ukr_gas.user_id_seq OWNED BY ukr_gas."user".id;


--
-- TOC entry 3515 (class 2604 OID 19564)
-- Name: account_tariff id; Type: DEFAULT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.account_tariff ALTER COLUMN id SET DEFAULT nextval('ukr_gas.account_tariff_id_seq'::regclass);


--
-- TOC entry 3516 (class 2604 OID 19571)
-- Name: address id; Type: DEFAULT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.address ALTER COLUMN id SET DEFAULT nextval('ukr_gas.address_id_seq'::regclass);


--
-- TOC entry 3517 (class 2604 OID 19758)
-- Name: auth id; Type: DEFAULT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.auth ALTER COLUMN id SET DEFAULT nextval('ukr_gas.auth_id_seq'::regclass);


--
-- TOC entry 3518 (class 2604 OID 19589)
-- Name: calculation id; Type: DEFAULT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.calculation ALTER COLUMN id SET DEFAULT nextval('ukr_gas.calculation_id_seq'::regclass);


--
-- TOC entry 3535 (class 2604 OID 19913)
-- Name: exchange_rate id; Type: DEFAULT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.exchange_rate ALTER COLUMN id SET DEFAULT nextval('ukr_gas.currency_rate_id_seq'::regclass);


--
-- TOC entry 3527 (class 2604 OID 19706)
-- Name: feedback id; Type: DEFAULT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.feedback ALTER COLUMN id SET DEFAULT nextval('ukr_gas.feedback_id_seq'::regclass);


--
-- TOC entry 3532 (class 2604 OID 19914)
-- Name: fuel_price id; Type: DEFAULT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.fuel_price ALTER COLUMN id SET DEFAULT nextval('ukr_gas.fuel_price_id_seq'::regclass);


--
-- TOC entry 3520 (class 2604 OID 19603)
-- Name: meter_reading id; Type: DEFAULT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.meter_reading ALTER COLUMN id SET DEFAULT nextval('ukr_gas.meter_reading_id_seq'::regclass);


--
-- TOC entry 3522 (class 2604 OID 19610)
-- Name: payment id; Type: DEFAULT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.payment ALTER COLUMN id SET DEFAULT nextval('ukr_gas.payment_id_seq'::regclass);


--
-- TOC entry 3530 (class 2604 OID 19742)
-- Name: personal_gas_account id; Type: DEFAULT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.personal_gas_account ALTER COLUMN id SET DEFAULT 'ukr_gas.gas_account_id_seq'::regclass;


--
-- TOC entry 3524 (class 2604 OID 19617)
-- Name: tariff id; Type: DEFAULT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.tariff ALTER COLUMN id SET DEFAULT nextval('ukr_gas.tariff_id_seq'::regclass);


--
-- TOC entry 3538 (class 2604 OID 20248)
-- Name: telegram_user id; Type: DEFAULT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.telegram_user ALTER COLUMN id SET DEFAULT nextval('ukr_gas.telegram_user_id_seq'::regclass);


--
-- TOC entry 3525 (class 2604 OID 19759)
-- Name: user id; Type: DEFAULT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas."user" ALTER COLUMN id SET DEFAULT nextval('ukr_gas.user_id_seq'::regclass);


--
-- TOC entry 3741 (class 0 OID 19561)
-- Dependencies: 211
-- Data for Name: account_tariff; Type: TABLE DATA; Schema: ukr_gas; Owner: postgres
--

COPY ukr_gas.account_tariff (id, discount, end_date, start_date, tariff_id) FROM stdin;
1	0	2024-01-01	2023-01-01	1
2	0	2024-01-01	2023-01-01	2
\.


--
-- TOC entry 3743 (class 0 OID 19568)
-- Dependencies: 213
-- Data for Name: address; Type: TABLE DATA; Schema: ukr_gas; Owner: postgres
--

COPY ukr_gas.address (id, apartment_number, city, house_number, region, street) FROM stdin;
5	\N	Львів	124	Львівська область	Зелена
6	\N	Івано-Франківськ	30	Івано-Франківська область	Вовчинецька
\.


--
-- TOC entry 3744 (class 0 OID 19577)
-- Dependencies: 214
-- Data for Name: auth; Type: TABLE DATA; Schema: ukr_gas; Owner: postgres
--

COPY ukr_gas.auth (id, email, password) FROM stdin;
8	andrium423arich@gmail.com	$2a$10$Tw5OgOotnxxJOycZL2nTFOYo.MMGoo8MuRx7mwlZWwpSs3kEoD8x6
12	andriumarich1@gmail.com	$2a$10$TraQNA8xgAUGHrYHqiLosOLD7l89.IfXigAc0Q1IPzIriEobUn9Ge
7	test@gmail.com	$2a$10$QBTMum6rvN4Wi0tiZCDFIeJC8P7aQSfUBbiNZnV6rC.5/K2rVjm9y
\.


--
-- TOC entry 3746 (class 0 OID 19586)
-- Dependencies: 216
-- Data for Name: calculation; Type: TABLE DATA; Schema: ukr_gas; Owner: postgres
--

COPY ukr_gas.calculation (id, personal_gas_account_id, amount_consumed, accrued_payment, paid_payment, balance, tariff_id, date) FROM stdin;
2	3	100	795.689	695.23	-140	1	2023-04-15
1	3	65	510	610	0	1	2023-05-15
\.


--
-- TOC entry 3766 (class 0 OID 19902)
-- Dependencies: 236
-- Data for Name: exchange_rate; Type: TABLE DATA; Schema: ukr_gas; Owner: postgres
--

COPY ukr_gas.exchange_rate (id, currency_from, currency_to, exchange_rate, date) FROM stdin;
1	USD	EUR	0.9127	2023-07-01
3	USD	USD	1.0000	2023-07-01
2	USD	UAH	36.9437	2023-07-01
10	USD	EUR	0.9161	2023-07-02
11	USD	UAH	36.9346	2023-07-02
12	USD	USD	1.0000	2023-07-02
13	USD	EUR	0.9161	2023-07-03
14	USD	UAH	36.9346	2023-07-03
15	USD	USD	1.0000	2023-07-03
16	USD	EUR	0.9112	2023-07-10
17	USD	UAH	36.6471	2023-07-10
18	USD	USD	1.0000	2023-07-10
19	USD	EUR	0.9087	2023-07-12
20	USD	UAH	36.9402	2023-07-12
21	USD	USD	1.0000	2023-07-12
22	USD	EUR	0.8977	2023-07-13
23	USD	UAH	36.5827	2023-07-13
24	USD	USD	1.0000	2023-07-13
25	USD	EUR	0.8910	2023-07-15
26	USD	UAH	36.9198	2023-07-15
27	USD	USD	1.0000	2023-07-15
28	USD	EUR	0.8892	2023-07-17
29	USD	UAH	36.6535	2023-07-17
30	USD	USD	1.0000	2023-07-17
31	USD	EUR	0.8907	2023-07-18
32	USD	UAH	36.6535	2023-07-18
33	USD	USD	1.0000	2023-07-18
36	USD	USD	1.0000	2023-07-22
35	USD	UAH	36.7384	2023-07-22
34	USD	EUR	0.8898	2023-07-22
\.


--
-- TOC entry 3754 (class 0 OID 19677)
-- Dependencies: 224
-- Data for Name: feedback; Type: TABLE DATA; Schema: ukr_gas; Owner: postgres
--

COPY ukr_gas.feedback (id, status, full_name, email, category_id, type_id, content, "timestamp") FROM stdin;
19	Open	Марич Андрій Петрович	andriumarich@gmail.com	3	3	Тест валіпьілтплвтпвапвавр	2023-06-07 00:58:18.34875+03
20	Open	Марич Андрій Петрович	andriumarich@gmail.com	2	1	Повідомлення	2023-06-07 14:25:36.487448+03
21	Open	Марич Андрій Петрович	andriumarich@gmail.com	1	2	Привіт Привіт	2023-06-07 14:26:14.926009+03
22	Open	Andrew	andriuma3rich@gmail.com	1	\N	sdsdg	2023-06-18 11:47:13.404516+03
23	Open	Марич Андрій Петрович	fdfd	2	3	dffdfdfsdfsdfsdfsd	2023-06-18 13:08:40.941581+03
24	Open	Марич Андрій Петрович	andriumarich@gmail.com	2	4	Thanks for creating this project	2023-07-02 20:57:19.026542+03
25	Open	Марич Андрій Петрович	andriumarich@gmail.com	2	3	Good day, I have a question about gas supply	2023-07-02 21:34:36.2552+03
26	Open	Марич Андрій Петрович	test@gmail.com	3	3	Good day, I have a question about gas supply	2023-07-02 21:38:08.51097+03
\.


--
-- TOC entry 3755 (class 0 OID 19684)
-- Dependencies: 225
-- Data for Name: feedback_category; Type: TABLE DATA; Schema: ukr_gas; Owner: postgres
--

COPY ukr_gas.feedback_category (id, category) FROM stdin;
1	Фінансові питання та розрахунки
2	Зміна постачальника
3	Тарифи та умови договору
\.


--
-- TOC entry 3756 (class 0 OID 19689)
-- Dependencies: 226
-- Data for Name: feedback_type; Type: TABLE DATA; Schema: ukr_gas; Owner: postgres
--

COPY ukr_gas.feedback_type (id, type) FROM stdin;
4	Інше
3	Запитання
2	Пропозиція
1	Скарга
\.


--
-- TOC entry 3765 (class 0 OID 19890)
-- Dependencies: 235
-- Data for Name: fuel_price; Type: TABLE DATA; Schema: ukr_gas; Owner: postgres
--

COPY ukr_gas.fuel_price (id, type, price, currency, country, date) FROM stdin;
17	DIESEL	1.363	USD	Ukraine	2023-07-17
16	LPG	0.651	USD	Ukraine	2023-07-17
18	GASOLINE	1.318	USD	Ukraine	2023-07-17
19	LPG	0.660	USD	Ukraine	2023-07-21
21	GASOLINE	1.329	USD	Ukraine	2023-07-21
22	LPG	0.661	USD	Ukraine	2023-07-22
20	DIESEL	1.351	USD	Ukraine	2023-07-21
23	DIESEL	1.352	USD	Ukraine	2023-07-22
24	GASOLINE	1.331	USD	Ukraine	2023-07-22
\.


--
-- TOC entry 3748 (class 0 OID 19600)
-- Dependencies: 218
-- Data for Name: meter_reading; Type: TABLE DATA; Schema: ukr_gas; Owner: postgres
--

COPY ukr_gas.meter_reading (id, date, meter_reading, personal_gas_account_id) FROM stdin;
2	2023-02-03	31820.00	1
3	2023-03-04	32105.00	1
1	2023-01-01	31456.00	1
7	2023-03-05	32301.00	1
22	2023-04-03	32405.00	1
23	2023-06-03	32301.00	2
34	2023-07-02	32405.00	2
25	2023-02-02	32504.00	1
37	2023-07-12	32607.00	1
35	2023-05-05	32115.00	3
36	2023-06-01	32225.00	3
\.


--
-- TOC entry 3750 (class 0 OID 19607)
-- Dependencies: 220
-- Data for Name: payment; Type: TABLE DATA; Schema: ukr_gas; Owner: postgres
--

COPY ukr_gas.payment (id, amount_paid, date, personal_gas_account_id) FROM stdin;
23504	420.00	2023-06-04 01:05:31.933074+03	1
23505	420.00	2023-06-04 01:20:23.361589+03	1
23506	420.00	2023-06-04 01:26:45.462584+03	1
23507	420.00	2023-06-04 01:39:19.028355+03	1
23508	420.00	2023-06-04 01:40:50.127043+03	1
23509	420.00	2023-06-04 01:46:57.204051+03	1
23510	420.00	2023-06-04 01:47:29.078009+03	1
23511	420.00	2023-06-04 01:47:36.209042+03	1
23512	25.50	2023-06-04 11:39:06.713319+03	1
23513	1400.00	2023-06-04 14:59:13.464675+03	1
23514	100.00	2023-06-04 14:59:37.324352+03	1
23502	200.00	2023-05-17 00:00:00+03	1
23503	455.23	2023-05-06 00:00:00+03	1
23515	1.00	2023-06-04 22:45:45.994186+03	1
23516	1.00	2023-06-04 22:47:12.842608+03	1
23520	10.00	2023-06-07 14:11:04.237352+03	1
23521	400.00	2023-06-07 14:28:32.854422+03	1
23522	10.00	2023-06-16 09:58:11.395986+03	1
23523	5.00	2023-06-18 13:14:07.352404+03	1
23524	1.00	2023-06-18 13:14:09.881224+03	1
23525	1.00	2023-06-28 23:02:54.563723+03	2
23526	1.00	2023-07-01 21:17:33.734275+03	1
23527	57.23	2023-07-02 20:56:44.923506+03	1
23533	150.00	2023-07-02 21:37:13.2763+03	1
23534	3.00	2023-07-10 11:41:56.697783+03	2
23535	3.00	2023-07-12 11:17:05.762702+03	1
23536	2.00	2023-07-12 11:17:11.363625+03	1
23537	1.00	2023-07-12 11:32:02.543813+03	1
23538	10.00	2023-07-12 12:00:17.250336+03	1
23539	5.00	2023-07-12 12:02:13.731304+03	1
23540	2.00	2023-07-12 12:02:16.230096+03	1
23541	5.00	2023-07-12 23:27:11.546767+03	1
23542	5.00	2023-07-12 23:29:01.479523+03	1
23543	35.00	2023-07-18 23:13:13.587322+03	3
23544	700.12	2023-07-19 14:46:29.819014+03	3
\.


--
-- TOC entry 3758 (class 0 OID 19707)
-- Dependencies: 228
-- Data for Name: person; Type: TABLE DATA; Schema: ukr_gas; Owner: postgres
--

COPY ukr_gas.person (id, first_name, last_name) FROM stdin;
1001	Андрій	Марич
1002	Олена	Задорожна
\.


--
-- TOC entry 3759 (class 0 OID 19720)
-- Dependencies: 229
-- Data for Name: personal_gas_account; Type: TABLE DATA; Schema: ukr_gas; Owner: postgres
--

COPY ukr_gas.personal_gas_account (id, person_id, user_id, balance, iec_number, gas_meter_number, address_id, account_number, tariff_id) FROM stdin;
1	1001	14	47.65	56XM16A507384234	342014	5	03234124	1
2	1002	\N	24.00	23IA16A509084853	975032	6	07753146	2
3	1002	14	735.12	98XC1609684888A3	869321	6	04682124	2
\.


--
-- TOC entry 3752 (class 0 OID 19614)
-- Dependencies: 222
-- Data for Name: tariff; Type: TABLE DATA; Schema: ukr_gas; Owner: postgres
--

COPY ukr_gas.tariff (id, plan, price) FROM stdin;
1	Річний	7.95689
2	Домашній	8.04655
\.


--
-- TOC entry 3771 (class 0 OID 20187)
-- Dependencies: 241
-- Data for Name: telegram_user; Type: TABLE DATA; Schema: ukr_gas; Owner: postgres
--

COPY ukr_gas.telegram_user (id, username, chat_id, verified, current_personal_gas_account_id, last_bot_state) FROM stdin;
13	andriymarych	521682137	\N	3	MAIN_MENU_SELECT
\.


--
-- TOC entry 3773 (class 0 OID 20213)
-- Dependencies: 243
-- Data for Name: telegram_user_personal_gas_account; Type: TABLE DATA; Schema: ukr_gas; Owner: postgres
--

COPY ukr_gas.telegram_user_personal_gas_account (user_id, personal_gas_account_id, verified) FROM stdin;
13	3	t
\.


--
-- TOC entry 3769 (class 0 OID 20170)
-- Dependencies: 239
-- Data for Name: token; Type: TABLE DATA; Schema: ukr_gas; Owner: postgres
--

COPY ukr_gas.token (id, token, type, expired, revoked, user_id) FROM stdin;
1	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlMUBnbWFpbC5jb20iLCJpYXQiOjE2ODg5MDA0NjMsImV4cCI6MTY4ODkzODg2M30.eNY_M2zeT7PVP6ZPAMlwBUP-xvh-h0gbpL4w92b3kOM	BEARER	t	t	14
2	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlMUBnbWFpbC5jb20iLCJpYXQiOjE2ODg5MTYzMTcsImV4cCI6MTY4ODk1NDcxN30.6QprDdPfOCSQzCiFUn36M3DAoGPhoCcfLy2U7S7jjXY	BEARER	t	t	14
3	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlMUBnbWFpbC5jb20iLCJpYXQiOjE2ODg5MjE2NTUsImV4cCI6MTY4OTAwODA1NX0.Ve2kjD5KBU96kAIgwCw8Yep8gw5xaNeDlJRMwEUennA	BEARER	t	t	14
4	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlMUBnbWFpbC5jb20iLCJpYXQiOjE2ODg5MjE2NjQsImV4cCI6MTY4OTAwODA2NH0.X1LXn2cfanc092jEUZYlutMfezZsrUtKKJBTsIzdNIg	BEARER	t	t	14
5	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlMUBnbWFpbC5jb20iLCJpYXQiOjE2ODg5MjE2NjUsImV4cCI6MTY4OTAwODA2NX0.0iTZ6KD61GQr78ib1RBnm0U0rog-Y5iPB_9xYjtG8ZA	BEARER	t	t	14
6	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlMUBnbWFpbC5jb20iLCJpYXQiOjE2ODg5MjE2NjYsImV4cCI6MTY4OTAwODA2Nn0.Acl9sA-nBMweVFBML4k7u5Y0uClf1AUAzinLK-ANqEk	BEARER	t	t	14
7	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlMUBnbWFpbC5jb20iLCJpYXQiOjE2ODg5MjcwMjAsImV4cCI6MTY4ODkyNzEwNn0.cuxUVrz98nViJI0O5k6Qp2AKB_X6dKjo9Soxns21PJM	BEARER	t	t	14
8	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlMUBnbWFpbC5jb20iLCJpYXQiOjE2ODg5MzE5MjYsImV4cCI6MTY4OTAxODMyNn0.E0oSAPnONzmB5JnfWiVVyqCKQajwomI02fl0QJZihN0	BEARER	t	t	14
9	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlMUBnbWFpbC5jb20iLCJpYXQiOjE2ODg5MzE5NDYsImV4cCI6MTY4ODkzMTk1NH0.OB2g1pPfTh3CRTE5Fsq70T4orWu3EFtJaXHDm4WL8gc	BEARER	t	t	14
10	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlMUBnbWFpbC5jb20iLCJpYXQiOjE2ODg5MzE5NzYsImV4cCI6MTY4ODkzMTk4NX0.F1cuoR_tO3R32Pr43OKBfjgpuMyQG5QC9uyW6zmq2o0	BEARER	t	t	14
11	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlMUBnbWFpbC5jb20iLCJpYXQiOjE2ODg5MzM3NTgsImV4cCI6MTY4OTAyMDE1OH0.6qvH1poymtuFhVs97JExSIG1VzDFMeBhB7Q3FQgDaGg	BEARER	t	t	14
12	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlMUBnbWFpbC5jb20iLCJpYXQiOjE2ODg5MzM4MDEsImV4cCI6MTY4OTAyMDIwMX0.r6DjxLQqYsEPoqSd3ncPzxQExXWWSJIGH-VOhT7qAPo	BEARER	t	t	14
13	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlMUBnbWFpbC5jb20iLCJpYXQiOjE2ODg5MzM4NDQsImV4cCI6MTY4OTAyMDI0NH0.Wob5qt7-JJxVPFv4KnfEvYcEGhQXAeaAXiCX35MTk0s	BEARER	t	t	14
14	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlMUBnbWFpbC5jb20iLCJpYXQiOjE2ODg5MzM4NzcsImV4cCI6MTY4OTAyMDI3N30.tfJDS_84SSdvvIFF_rqMZ_Yq8t8uSBjvsAFYbJpP5eA	BEARER	t	t	14
15	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlMUBnbWFpbC5jb20iLCJpYXQiOjE2ODg5MzQwMjksImV4cCI6MTY4OTAyMDQyOX0.rUxPS8AWk-msFqJxCQYrlKIo8IEeuu8vCeYPaTX-NQI	BEARER	t	t	14
16	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlMUBnbWFpbC5jb20iLCJpYXQiOjE2ODg5MzQ0NjksImV4cCI6MTY4OTAyMDg2OX0.j0UuNDYHpD2uY3Y6UDKkgmekI4PXR3wCeBUzocDOHZQ	BEARER	t	t	14
17	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlMUBnbWFpbC5jb20iLCJpYXQiOjE2ODg5MzQ2MjksImV4cCI6MTY4OTAyMTAyOX0.KnLFRUHqvoDH2_NAEBIRkLEl0BNAYrBGl2ZvFJzvu74	BEARER	t	t	14
18	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlMUBnbWFpbC5jb20iLCJpYXQiOjE2ODg5MzQ2ODgsImV4cCI6MTY4OTAyMTA4OH0.zASHQSXpnVaYIuUoFzRiUIrNpyTnMAvERYZ3Ym4GXIU	BEARER	t	t	14
19	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlMUBnbWFpbC5jb20iLCJpYXQiOjE2ODg5MzYwODgsImV4cCI6MTY4OTAyMjQ4OH0.meWfk7lcGJhANoQUgJ6TfLujFXix7KeUIvl6Sm6hdzo	BEARER	t	t	14
20	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg4OTM2MjMwLCJleHAiOjE2ODkwMjI2MzB9.7e_qsPMsGZOUe1zCWhqJm2TDIlJdUKw5cdP-Lhp9mTA	BEARER	t	t	14
21	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg4OTM2NDY5LCJleHAiOjE2ODkwMjI4Njl9.gx_C0Y-FC9ECpLYN4HD9vMGwO1szy3CplGwvH3f59OU	BEARER	t	t	14
22	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg4OTc2MTQzLCJleHAiOjE2ODkwNjI1NDN9.IVdvb-Iuaztdeags3Nhqu_q2JlU5rNv2ICmCTDK5n5A	BEARER	t	t	14
23	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg4OTc2Mjc5LCJleHAiOjE2ODkwNjI2Nzl9.RHbH3xdWjV1sIkBc8yNuKIV0W-KXOTmi7WQSI8LDdBU	BEARER	t	t	14
24	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg4OTc2ODExLCJleHAiOjE2ODkwNjMyMTF9.7RRqInws9qdQa5wECRZQjI8DANPVpvwYfgJMn9goDKw	BEARER	t	t	14
25	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg4OTc2OTA5LCJleHAiOjE2ODkwNjMzMDl9.3y_0mTQ6wkgt2XJ8lKofRkQYsMqbVArIvLR1ZW3Mlug	BEARER	t	t	14
26	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg4OTc2OTcxLCJleHAiOjE2ODkwNjMzNzF9.AthVPGbnNklSRDWYfgIOnKsOYxVjdmDJrvwmaVGewWs	BEARER	t	t	14
27	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg4OTc3MDcyLCJleHAiOjE2ODkwNjM0NzJ9.nPv93NvFcNhV5epZLCvmUiXNdeRQ_tBFsZeu6ONqrsE	BEARER	t	t	14
28	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg4OTc3MjQ5LCJleHAiOjE2ODkwNjM2NDl9.-Mu-jqBLK5wGTrzu3m7Im2OwPsm1V4O0psiQmtTADRI	BEARER	t	t	14
29	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg4OTc3NzcwLCJleHAiOjE2ODkwNjQxNzB9.T1WIFHr2w0RTlSoWrt8kXN8IzDuBkaLjvU-eZthNQZQ	BEARER	t	t	14
30	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg4OTc4MjQ1LCJleHAiOjE2ODkwNjQ2NDV9.tXcl36AUMHZ7UUPK4J0KAkWZfM32FO3HEG6jfUApbJ8	BEARER	t	t	14
31	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg4OTc4MjQ2LCJleHAiOjE2ODkwNjQ2NDZ9.o5bWZmq4Eq5V8hGEu5epNbPDWjx0bUsC7rmv6alAyDk	BEARER	t	t	14
32	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg4OTc4NDcyLCJleHAiOjE2ODkwNjQ4NzJ9.bWnATaOVWMgj6cPRzsnqIdXpIyBSOoypn9cS0bEcaCc	BEARER	t	t	14
33	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTA3NTM0LCJleHAiOjE2ODkxOTM5MzR9.ih8PK4qF1rqQNdWxGE-bAovK7UPpX7D0ybnT0Oct4ps	BEARER	t	t	14
34	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTA5MjUwLCJleHAiOjE2ODkxOTU2NTB9.hGhz1y_DT6g38vG1QqwvIykPJP7icBza2ecV9Rx68Zc	BEARER	t	t	14
35	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTQ4MDU5LCJleHAiOjE2ODkyMzQ0NTl9.cEylZbvuvRwwBeUJ1drM2ua1JfskjR_epcQjvouo7rk	BEARER	t	t	14
37	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTQ5MTY2LCJleHAiOjE2ODkyMzU1NjZ9.bHtOzGkRFUp4Gjz4yzyp-GjacWI8aRq0yLbrr_u9eVI	BEARER	t	t	14
36	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTQ5MTYwLCJleHAiOjE2ODkyMzU1NjB9.mm8JaLhyjh82wztaG5d44h0i3B3F4bONnuFtYFe6Cnc	BEARER	t	t	14
38	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTQ5Njk2LCJleHAiOjE2ODkyMzYwOTZ9.Dzo7zxx6L7NBqIEOkdR7iAebnknV1VgsZxX3iceU83s	BEARER	t	t	14
39	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTUwNzE5LCJleHAiOjE2ODkyMzcxMTl9.oCnSWmk_wbMegMrhal19mCn9GswP2OKT4enTzpXLm50	BEARER	t	t	14
40	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTUwOTEzLCJleHAiOjE2ODkyMzczMTN9.t_wgwSRE_5uSfeQVc1kGbVCuecFLiAtkBAeyCwbXqY8	BEARER	t	t	14
41	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTUxNTMwLCJleHAiOjE2ODkyMzc5MzB9.DnuhmOBFuu9LpJT3CqsigwUId6gXPpMikQ1QlrJlDEY	BEARER	t	t	14
42	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTUxNTU4LCJleHAiOjE2ODkyMzc5NTh9.iQd0KJFXMPO3Rcdw9E0dIuZBaRY-_yuRUda3BgzCpZc	BEARER	t	t	14
43	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTUxNTcyLCJleHAiOjE2ODkyMzc5NzJ9.JJXJsekPM3m7qMPpUrWOjV9buxk8YckVt-NqvFzGJwA	BEARER	t	t	14
44	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTUxNTkyLCJleHAiOjE2ODkyMzc5OTJ9.Yx3qlysNV63DA8odWk0U2SkswmGl8Yn2kh1-AE825Ec	BEARER	t	t	14
45	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTUxNjIyLCJleHAiOjE2ODkyMzgwMjJ9.GQeHl_YYVTTGGrXBaC6p-8ORre1IN3_9Lwn5ZNZ1EIA	BEARER	t	t	14
46	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTUxOTQ3LCJleHAiOjE2ODkyMzgzNDd9.gadBGKEvAf0qJNoeIcrPjdioZOF5eKRE6S722v2RCMc	BEARER	t	t	14
47	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTUyNDAzLCJleHAiOjE2ODkyMzg4MDN9.ZRsbyRjuGhi0NBe7K6JBeK0yFJoH7Rf0U1Kht6IicF4	BEARER	t	t	14
48	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTUzMzc3LCJleHAiOjE2ODkyMzk3Nzd9.MrVhE1_3N2rq2anyKNbOwWUgmqfUQYwES4on2hB3MfQ	BEARER	t	t	14
49	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTU4MzAyLCJleHAiOjE2ODkyNDQ3MDJ9.02INJPgWRkAZW2GHkMVaG7vrsvhHrku2kkw5AFUi-lk	BEARER	t	t	14
50	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTcyNTAwLCJleHAiOjE2ODkxNzI1MDh9.rqnVZDLaoMsIzAdpCp5oS1mTWlGUqk3KnDnQ26mlqlI	BEARER	t	t	14
51	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTcyNjM0LCJleHAiOjE2ODkxNzI2NDN9.Aw0zj8q5c_x2D6DmpxOvNf-lQNawgLuCYGwLEUL7toM	BEARER	t	t	14
52	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTc2OTgxLCJleHAiOjE2ODkxNzY5ODl9.2c5u7RdRF-ddYmlMrOkEJVinnrfXKSPBjVmukCOmEgs	BEARER	t	t	14
53	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTc2OTk0LCJleHAiOjE2ODkxNzcwMDN9.EZFwTUGOXdgrQHBf1nQMXeQ-TgOLar6e0aGbbZlPCE8	BEARER	t	t	14
54	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTc3MDMyLCJleHAiOjE2ODkxNzcwNDF9.-ez5ngEeqxzanotaY5n9Picynz1FplHusSQTmN-AjeQ	BEARER	t	t	14
55	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTc3MDgxLCJleHAiOjE2ODkxNzcwOTB9.W2kr7nmqr75hd0jZesob1og71aUmusycoqdwRWcmhCE	BEARER	t	t	14
56	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTc3MTM0LCJleHAiOjE2ODkxNzcxNDN9.zO64mRMehW8R9a7zjxqBsfGuWm23YfFaVuuEJa1XpH8	BEARER	t	t	14
57	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTc3NjgyLCJleHAiOjE2ODkxNzc2OTF9.RBUGHbAQcI_0izHHnMPPmGq6OZ2xBh7ik5XUn9PwEVA	BEARER	t	t	14
58	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTc3ODY4LCJleHAiOjE2ODkxNzc4Nzd9.7DnpTi6bcWDvksY2trj76hsL4PLyo3cw4UZ4dsIcKJA	BEARER	t	t	14
59	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTc3OTc5LCJleHAiOjE2ODkxNzc5ODh9.JJeFSycpI7rg6xHNEjaBGX3CNZ0z0D1iCKkAZHyIL0Y	BEARER	t	t	14
60	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTc4OTI5LCJleHAiOjE2ODkxNzkwNDl9.m_VJLeYtXYNnxj0BO4bdcVEN3E9ZKEgnqx0heSGJnPI	BEARER	t	t	14
61	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTgxOTk4LCJleHAiOjE2ODkxODIxMTh9.7G4_aLyGiRLjXAqnmDWyEUN9uNLtOhmso_vSJjkng9k	BEARER	t	t	14
62	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTgyMjQ1LCJleHAiOjE2ODkxODIyNTd9.lbO4OoIK1evodp6qK6ygg5DBkvY2fQtd2djGwb0zm8g	BEARER	t	t	14
63	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTgzNDk3LCJleHAiOjE2ODkxODM1MDl9.iM8J1b_Q8gUaUrBHmKJ6tG3kDL4SOV1GtiwWzoqo6kA	BEARER	t	t	14
64	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTgzNTA2LCJleHAiOjE2ODkxODM1MTh9.qw_D0o8DgYX9kdoR3QMOCA4-ld0pibv29akPpN9qof8	BEARER	t	t	14
65	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTgzNjgyLCJleHAiOjE2ODkxODM2OTR9.pVK84PX1a6sFpsdHg4nURbgwPOEd4Ofr9cwDYYh4WuU	BEARER	t	t	14
66	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTgzODExLCJleHAiOjE2ODkxODM4MjN9.53b_Q0wMrjQvy9zLnBWOOzN-dKB5W0h4BNgHdt6A3hg	BEARER	t	t	14
67	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTgzODYzLCJleHAiOjE2ODkxODM4NzV9.wG6alkdDgP_SQ2ftReFy-PEtqAtXqPrHFeT4tuzNroU	BEARER	t	t	14
68	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTgzOTA0LCJleHAiOjE2ODkxODM5MTZ9.z5wIFCJdVBuNS8hyXe8gCdxEPZKBJaPXg3ZbSnvwAV0	BEARER	t	t	14
69	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTgzOTg5LCJleHAiOjE2ODkxODQwMDF9.iJIN1OttWDgRc-hiepW772MPWDoNH50xrcCa_4VBx_Y	BEARER	t	t	14
70	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg0MTI4LCJleHAiOjE2ODkxODQxNDB9.BCPFmApk43gLA3WPR3Fktyhtts9NhoBPIpbxF2-bWgw	BEARER	t	t	14
72	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg0MTY2LCJleHAiOjE2ODkxODQxNzh9.2JGzJPlWPF-XUMe8xReZmNIyAY4rnQ5G4gzGJlDHlS8	BEARER	t	t	14
71	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg0MTQwLCJleHAiOjE2ODkxODQxNTJ9.jb4Oj_Ki2XfsdiHPJTe6gYdKRbVvAMI_vACWRfcAf2k	BEARER	t	t	14
73	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg0MTY2LCJleHAiOjE2ODkxODQxNzh9.2JGzJPlWPF-XUMe8xReZmNIyAY4rnQ5G4gzGJlDHlS8	BEARER	t	t	14
74	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg0MTgwLCJleHAiOjE2ODkxODQxOTJ9.ZaoDzK88Zn4yBx3pIWcL3mEBom8U4sNIv-jS6FlVkIE	BEARER	t	t	14
75	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg0MTgwLCJleHAiOjE2ODkxODQxOTJ9.ZaoDzK88Zn4yBx3pIWcL3mEBom8U4sNIv-jS6FlVkIE	BEARER	t	t	14
76	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg0MTgxLCJleHAiOjE2ODkxODQxOTN9.lnJKlqWU7zAoe5OvxSamzUc6B4WWVjWFqFC7cYYv6Is	BEARER	t	t	14
77	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg0MTgxLCJleHAiOjE2ODkxODQxOTN9.lnJKlqWU7zAoe5OvxSamzUc6B4WWVjWFqFC7cYYv6Is	BEARER	t	t	14
78	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg0MTgxLCJleHAiOjE2ODkxODQxOTN9.lnJKlqWU7zAoe5OvxSamzUc6B4WWVjWFqFC7cYYv6Is	BEARER	t	t	14
79	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg0MTgxLCJleHAiOjE2ODkxODQxOTN9.lnJKlqWU7zAoe5OvxSamzUc6B4WWVjWFqFC7cYYv6Is	BEARER	t	t	14
80	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg0MTgyLCJleHAiOjE2ODkxODQxOTR9.BxMS7UcKAjBgTjywpEqowP_B_esOVY3OoPOjJVkS4Nw	BEARER	t	t	14
81	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg0MTgyLCJleHAiOjE2ODkxODQxOTR9.BxMS7UcKAjBgTjywpEqowP_B_esOVY3OoPOjJVkS4Nw	BEARER	t	t	14
82	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg0MTgzLCJleHAiOjE2ODkxODQxOTV9.VB-66lA9z_IJbJM4-7Z0q5NbCm7G4WoC2OY2_WUwVx4	BEARER	t	t	14
83	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg0MTgzLCJleHAiOjE2ODkxODQxOTV9.VB-66lA9z_IJbJM4-7Z0q5NbCm7G4WoC2OY2_WUwVx4	BEARER	t	t	14
84	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg0MjIxLCJleHAiOjE2ODkxODQyMzN9.HC6Bb74JkP8zWAVeNNEZqYx-tc0h_nh1mU9fHOCvk8M	BEARER	t	t	14
85	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg0MjIxLCJleHAiOjE2ODkxODQyMzN9.HC6Bb74JkP8zWAVeNNEZqYx-tc0h_nh1mU9fHOCvk8M	BEARER	t	t	14
86	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg0MjIyLCJleHAiOjE2ODkxODQyMzR9.NV72rXtbOhtA37BFAUbP437wL5paj5MPnPGkTdgDUbw	BEARER	t	t	14
87	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg0MjIyLCJleHAiOjE2ODkxODQyMzR9.NV72rXtbOhtA37BFAUbP437wL5paj5MPnPGkTdgDUbw	BEARER	t	t	14
88	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg0MjYzLCJleHAiOjE2ODkxODQyNzV9.xC1HOsz8atRLhryFH6j7XBsSHqjFEBAYbM8W8zdzFNo	BEARER	t	t	14
89	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg0MjY0LCJleHAiOjE2ODkxODQyNzZ9.2iXXRDameeMJIJYJsRjTtSpmRoCnkGOI3UmCSGJAGyk	BEARER	t	t	14
90	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg0Mjg5LCJleHAiOjE2ODkxODQzMDF9.fzpetH0v4tvF_u4aTtZTj-WOKls1ykRe8OnpJdageZE	BEARER	t	t	14
91	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg0MzI0LCJleHAiOjE2ODkxODQzMzZ9.K-PWrlu26yVWJoyut1FFuUDAged-n2NgyRCGJcLcSOQ	BEARER	t	t	14
92	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg0MzI0LCJleHAiOjE2ODkxODQzMzZ9.K-PWrlu26yVWJoyut1FFuUDAged-n2NgyRCGJcLcSOQ	BEARER	t	t	14
93	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg0MzY2LCJleHAiOjE2ODkxODQzNzh9.qICftaHhrnNm0rpwXdYmyEhfVtwXvMNXquA8FsskK_s	BEARER	t	t	14
94	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg1MzY4LCJleHAiOjE2ODkxODUzODB9.MWFeex_gZxGIhnCwkzZC-o2xiROJ2gw9lGdYKf7OKss	BEARER	t	t	14
95	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg1MzY5LCJleHAiOjE2ODkxODUzODF9.0ciyLEvY5UmTsiSILB1UBt8o9RefOsUIWQF4VnUMuy0	BEARER	t	t	14
96	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg1Mzg1LCJleHAiOjE2ODkxODUzOTd9.4pbtwKxhKdG4fbVsEpag3VfHgib6xfV4D5ICMcB0DOw	BEARER	t	t	14
97	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg1NDg1LCJleHAiOjE2ODkxODU0OTd9.o049qtGyZhUJXPqVBg4JyDow1mSEUWKDW8BOkDrHWsI	BEARER	t	t	14
98	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg1NTA2LCJleHAiOjE2ODkxODU1MTh9.nVix7dduglRz1lCt5s9sgIHi1dtp3zL6kbyiT4q5pqk	BEARER	t	t	14
99	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg1NTgyLCJleHAiOjE2ODkxODU1OTR9.PNqk8qiD77rTRUKqh8rBNVaNaonvK4Ch_HklkE1MqLs	BEARER	t	t	14
100	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg1NjMwLCJleHAiOjE2ODkxODU2NDJ9.3cCuDVwmXQgA3yYT9-jUiH0q8EoMkwtgl3nmTbGY8-s	BEARER	t	t	14
101	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg1NjM5LCJleHAiOjE2ODkxODU3NTl9.O9PTf-y-gFEcmw091AwNEn6F4oRooZTC_jJsNXN2Yew	BEARER	t	t	14
102	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg1ODI5LCJleHAiOjE2ODkxODU5NDl9.5sSi0v6Lb9dwc8aJ_7HZw6tEGjgU2sNVUFImydtlnxk	BEARER	t	t	14
103	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg2MDAwLCJleHAiOjE2ODkxODYxMjB9.mCf6kHDmQ2xbwkQ1lqrLK8nqG15IMDeKWRWnwpk_bdk	BEARER	t	t	14
104	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg2MTA2LCJleHAiOjE2ODkxODYyMjZ9.yPCHj33KShbE2iSDy940a9KZAU9H0SL0oeHZdEp9yRk	BEARER	t	t	14
105	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg2MTU0LCJleHAiOjE2ODkxODYyNzR9.nLFIp8zlIRmkeAmQxaozgNC-NQ5QhE58CoPPTMwgYQg	BEARER	t	t	14
106	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg2MjE1LCJleHAiOjE2ODkxODYzMzV9.OeCo4xst8cK3QEKA19o8zizntHbwVjV0Q3s_XHEKc-g	BEARER	t	t	14
107	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg2MzQ2LCJleHAiOjE2ODkxODY0NjZ9.FfDD067bbD1jEdPZMEsZRTgTFU-BrgPXTEzzeYdeGH8	BEARER	t	t	14
109	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg2NTgxLCJleHAiOjE2ODkxOTg1ODF9._OpPEc9VIDGwuLP_SY9vBglmH3X3QrPCAFR2mGWrfuU	BEARER	t	t	14
108	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg2NDczLCJleHAiOjE2ODkxOTg0NzN9.Xqb30BUXTxetXtose7QhbTEPYJAY36MgBvbrm6jvnSA	BEARER	t	t	14
110	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg2NjA0LCJleHAiOjE2ODkxOTg2MDR9.ONEsUw3AARwphGA6eSIpvKauZYKKZk1FSdaw2eFDFB8	BEARER	t	t	14
111	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg2NjEzLCJleHAiOjE2ODkxOTg2MTN9.6FvNyDg8OF5w1BHH3wJXDXLbEm0v3CsRdFFj2AxraLA	BEARER	t	t	14
112	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg3MzU3LCJleHAiOjE2ODkxOTkzNTd9.dP233dxv8VjXGJ_epLt3vlT9iyGG7KKJDISBfPBtJ0w	BEARER	t	t	14
113	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg3NDU4LCJleHAiOjE2ODkxODc0NTl9.Rs3LbyGqQVIu_EoJYz7V5hss1VBFPbJh85JGc8IC_dA	BEARER	t	t	14
114	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg3NDU5LCJleHAiOjE2ODkxODc0NjB9.8XAbTRYTVxGlYHmx-YiQlwLsWG1WBN-V39Lb4dkJCxw	BEARER	t	t	14
115	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg3NDYxLCJleHAiOjE2ODkxODc0NjN9.FP8xeYUtvyxLzQUc0RxdrDkbje-hJvWU6ui_BU9fcAE	BEARER	t	t	14
116	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg3NDYzLCJleHAiOjE2ODkxODc0NjR9.bBIJTHqixg8cNhZx_lYgbKEXlIpnwuziJ4S7mfjYob4	BEARER	t	t	14
117	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg3NDY0LCJleHAiOjE2ODkxODc0NjV9.HeR-jRUw2FXdqMXQ1rUJM6Trs24FIIlAslCCMo26hPs	BEARER	t	t	14
118	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg4NDEzLCJleHAiOjE2ODkxODg0MjV9.4Sa85QEWB3a16pyKikklTXIlRWaRPRb6hamEuvdoBAM	BEARER	t	t	14
119	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg4NDI1LCJleHAiOjE2ODkxODg0Mzd9.JcyPNZAzDA-AD9ZSlp_GGlRnUjBw7p4zurcghbfyP9Q	BEARER	t	t	14
120	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg4NDM3LCJleHAiOjE2ODkxODg0NDl9.6bqnm7bbRy_733Rbh5umaw0PZgzRKiTI1WKc0vCrNhM	BEARER	t	t	14
121	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg4NDUwLCJleHAiOjE2ODkxODg0NjJ9.8Yzg2HRPXnHlBEaDZAzV5zqN3I5_a2N8amRvNs91ark	BEARER	t	t	14
122	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg4NDYyLCJleHAiOjE2ODkxODg0NzR9.ZcsR4WJT1I_7evHmXIP3O-zRbTxJKRZ6MwQ4IeX39tY	BEARER	t	t	14
123	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg4NDc0LCJleHAiOjE2ODkxODg0ODZ9.0zlfunumzEflSKTVZMtyz4LR8QagHS2nz4-0cBYjMaw	BEARER	t	t	14
124	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg4NTA0LCJleHAiOjE2ODkxODg1MTZ9.5C0_Tr2_Q6Gjp46snYdnWIK_dXDEHlzJn8bPi0Rhxv8	BEARER	t	t	14
125	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg4NTE2LCJleHAiOjE2ODkxODg1Mjh9.EseFs6Esw4No8ludZS1aDjUYXQJ52xGI9Yq5Km4Z-oo	BEARER	t	t	14
126	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg4NjU4LCJleHAiOjE2ODkxODg2NzB9.DgURtffpjptGCkL1oyF6sStsSwWUpohCVpWdTU0_u_g	BEARER	t	t	14
127	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg4NzAyLCJleHAiOjE2ODkxODg3MTR9.S6kbeqPWf0hVMyNRily1cMt3r395nf3ymYEQTrS-uEA	BEARER	t	t	14
128	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTg4NzUyLCJleHAiOjE2ODkxODg3NjR9.0SocwtV-zlIHIWxm_UMWzWmC7pGa1fh8aYhfD1WxgAg	BEARER	t	t	14
129	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkxNDY1LCJleHAiOjE2ODkxOTE0Nzd9.tLel-TeJ9nJoYoIC2-h5JcAANC2wYdyOp8Z70sMhh58	BEARER	t	t	14
130	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkxNTU3LCJleHAiOjE2ODkxOTE1Njl9.6BocCfIw-XqNmd7ONfuGmnodEF6qqR0MDDZgF_QISlg	BEARER	t	t	14
131	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkxNTczLCJleHAiOjE2ODkxOTE1ODV9.l0Gdj2lBD181sWp3Lg492LG63t4ztETSwe-nfw5MKpI	BEARER	t	t	14
132	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkxNTg1LCJleHAiOjE2ODkxOTE1OTd9.LnYBLMo0V0_mfphJAP5azc40bBX06-3rqlnu-Kaqkq8	BEARER	t	t	14
133	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkxNzc1LCJleHAiOjE2ODkxOTE3ODd9.78hki9g4ZSnZG9ZtxGj_OcL2b4anyaoO7Sc78Y7o6ko	BEARER	t	t	14
134	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkxODM3LCJleHAiOjE2ODkxOTE4NDl9.yi1AOh_2ucw_DTVwECTynB-Ef9IJaL4dbTkhIY2WKtg	BEARER	t	t	14
135	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkxODUwLCJleHAiOjE2ODkxOTE4NjJ9.7vJLtx9RnczVWtJuxNIH7oVWaEKwU2noCK-W-8s9-pY	BEARER	t	t	14
136	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkxODY2LCJleHAiOjE2ODkxOTE4Nzh9.Je3Mej7sNDho6aDVWHYItEEwpEhMSn06mQM6oQc82kg	BEARER	t	t	14
137	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkxOTI1LCJleHAiOjE2ODkxOTE5Mzd9.jE7dOxrO809TSbfu1rv88gvreVRIF2FC3LfbhVzLlQg	BEARER	t	t	14
138	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkxOTM3LCJleHAiOjE2ODkxOTE5NDl9.u-9UxnO7sQu8kn-pYP-BXgY2zwkcaV4QmTFMYTFJ-jI	BEARER	t	t	14
139	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkyOTM2LCJleHAiOjE2ODkxOTI5NDh9.-dMebBqmCzlTx_DZLY17AG7yCEC2wCgXlvOmrF0Yho4	BEARER	t	t	14
140	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzMDk0LCJleHAiOjE2ODkxOTMxMDZ9.KbnJr6Wej3ITedRcf0_0iKtCY4ECN7o0oc6y7n0ZZ-I	BEARER	t	t	14
141	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzMTEwLCJleHAiOjE2ODkxOTMxMjJ9.ERwG6PgE1b6pS8ujhNYZ_6HPCFHmuUXQB_cL40CPjfU	BEARER	t	t	14
150	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzMTQwLCJleHAiOjE2ODkxOTMxNTJ9.GldzN0RdxIGrFtCMynYnAHjex2wG-11O8qR8qMvPtfo	BEARER	t	t	14
143	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzMTQwLCJleHAiOjE2ODkxOTMxNTJ9.GldzN0RdxIGrFtCMynYnAHjex2wG-11O8qR8qMvPtfo	BEARER	t	t	14
144	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzMTQwLCJleHAiOjE2ODkxOTMxNTJ9.GldzN0RdxIGrFtCMynYnAHjex2wG-11O8qR8qMvPtfo	BEARER	t	t	14
145	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzMTQwLCJleHAiOjE2ODkxOTMxNTJ9.GldzN0RdxIGrFtCMynYnAHjex2wG-11O8qR8qMvPtfo	BEARER	t	t	14
147	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzMTQwLCJleHAiOjE2ODkxOTMxNTJ9.GldzN0RdxIGrFtCMynYnAHjex2wG-11O8qR8qMvPtfo	BEARER	t	t	14
149	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzMTQwLCJleHAiOjE2ODkxOTMxNTJ9.GldzN0RdxIGrFtCMynYnAHjex2wG-11O8qR8qMvPtfo	BEARER	t	t	14
146	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzMTQwLCJleHAiOjE2ODkxOTMxNTJ9.GldzN0RdxIGrFtCMynYnAHjex2wG-11O8qR8qMvPtfo	BEARER	t	t	14
153	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzMTQwLCJleHAiOjE2ODkxOTMxNTJ9.GldzN0RdxIGrFtCMynYnAHjex2wG-11O8qR8qMvPtfo	BEARER	t	t	14
142	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzMTI4LCJleHAiOjE2ODkxOTMxNDB9.urHt3qjHU2AKuKaIsr9Cpy3es8baRPfKIfig3fIGBSM	BEARER	t	t	14
151	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzMTQwLCJleHAiOjE2ODkxOTMxNTJ9.GldzN0RdxIGrFtCMynYnAHjex2wG-11O8qR8qMvPtfo	BEARER	t	t	14
152	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzMTQwLCJleHAiOjE2ODkxOTMxNTJ9.GldzN0RdxIGrFtCMynYnAHjex2wG-11O8qR8qMvPtfo	BEARER	t	t	14
154	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzMTk2LCJleHAiOjE2ODkxOTMyMDh9.4P2NrTam4nvDIq5nvGzCPg6NhMLe5lqW0744NvmBpvE	BEARER	t	t	14
167	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzNDE5LCJleHAiOjE2ODkxOTM0MzF9.lM2huNvSKNBC2CmCinAy7yXmXs1DkAIXcyKb5KHRTOg	BEARER	t	t	14
172	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzNDE5LCJleHAiOjE2ODkxOTM0MzF9.lM2huNvSKNBC2CmCinAy7yXmXs1DkAIXcyKb5KHRTOg	BEARER	t	t	14
155	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzMjA4LCJleHAiOjE2ODkxOTMyMjB9.OjpDnSu88isg85jSPNEX3JSBK977xKDmSxfyWlPmTXA	BEARER	t	t	14
162	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzMzc5LCJleHAiOjE2ODkxOTMzOTF9._JD590EMfl-2zBw-F39ekpU3RonOxNUI04cmuVfR4oc	BEARER	t	t	14
163	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzMzg1LCJleHAiOjE2ODkxOTMzOTd9.9lcvcNb-1zND51s5f14ISE3kKMyeMKZUcY2-YDHEkAA	BEARER	t	t	14
166	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzNDE5LCJleHAiOjE2ODkxOTM0MzF9.lM2huNvSKNBC2CmCinAy7yXmXs1DkAIXcyKb5KHRTOg	BEARER	t	t	14
156	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzMjIwLCJleHAiOjE2ODkxOTMyMzJ9.g3thTbogcb5iv9g3blDTEFmyF1B_BQ0MiP945gpo6Gg	BEARER	t	t	14
158	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzMjc4LCJleHAiOjE2ODkxOTMyOTB9.k1JITLA5dOiL7O-frd_9Ek8TNCZ0GyG_9s7gGsEg6E0	BEARER	t	t	14
161	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzMzY3LCJleHAiOjE2ODkxOTMzNzl9.JUi84nKrv4lLYppVAxg1kIiMlvBYg5LU2DL-lssYw5Q	BEARER	t	t	14
168	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzNDE5LCJleHAiOjE2ODkxOTM0MzF9.lM2huNvSKNBC2CmCinAy7yXmXs1DkAIXcyKb5KHRTOg	BEARER	t	t	14
157	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzMjM1LCJleHAiOjE2ODkxOTMyNDd9.BcbaXAGmo5UyL7b9a023dZW6UsEBzvzGzlCgXJHrBC8	BEARER	t	t	14
160	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzMzYxLCJleHAiOjE2ODkxOTMzNzN9.PenHpaYp6jffxkPkIPIQdYDUwrCVtOu0AEfrtSENvBg	BEARER	t	t	14
164	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzNDA3LCJleHAiOjE2ODkxOTM0MTl9.UsPnOjveclKBuPdmJDSxPtYLq49YY5UBBvjan9QeL9E	BEARER	t	t	14
170	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzNDE5LCJleHAiOjE2ODkxOTM0MzF9.lM2huNvSKNBC2CmCinAy7yXmXs1DkAIXcyKb5KHRTOg	BEARER	t	t	14
159	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzMzIwLCJleHAiOjE2ODkxOTMzMzJ9.WdDYdl8VhdJcq0seI0xfJ8Xx_e9XUI8N7vWDrjdCa_k	BEARER	t	t	14
169	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzNDE5LCJleHAiOjE2ODkxOTM0MzF9.lM2huNvSKNBC2CmCinAy7yXmXs1DkAIXcyKb5KHRTOg	BEARER	t	t	14
165	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzNDE5LCJleHAiOjE2ODkxOTM0MzF9.lM2huNvSKNBC2CmCinAy7yXmXs1DkAIXcyKb5KHRTOg	BEARER	t	t	14
171	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzNDE5LCJleHAiOjE2ODkxOTM0MzF9.lM2huNvSKNBC2CmCinAy7yXmXs1DkAIXcyKb5KHRTOg	BEARER	t	t	14
173	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzNDQxLCJleHAiOjE2ODkxOTM0NDh9.MeA4WBUSmkekUMlaWoeLTOOeMtrxicAf-SnMWr3HTKE	BEARER	t	t	14
174	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzNDUyLCJleHAiOjE2ODkxOTM0NTl9.A_xG7RsavUKzm11NpvvrGU78OCHVrERoxWE6ZV75uzg	BEARER	t	t	14
175	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzNjA3LCJleHAiOjE2ODkyODAwMDd9.Bgs3TEO9OXUOjjU0GeDK_tN5SQ9Xcz1a-WmKnIrSMOw	BEARER	t	t	14
176	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTk0NDMxLCJleHAiOjE2ODkyODA4MzF9.UsdE8Jp0Mz6UYyf3rbh0_l_ZE2I8Z3Bzy0rtduByPSg	BEARER	t	t	14
177	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MjM4NzY4LCJleHAiOjE2ODkzMjUxNjh9.Pa0dQz6_Lez1IkEu1e4JFQ-XXr0MQ0AMeAL8o0UOWWM	BEARER	t	t	14
178	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MjM5MDc2LCJleHAiOjE2ODkzMjU0NzZ9.nGgR3eGyLJNMSP5D26WfhkSvHXCs1WoWnLJw4Ai1R0w	BEARER	t	t	14
179	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MzQyNDkyLCJleHAiOjE2ODk0Mjg4OTJ9.InYR2ucXlMENCFWgSC03vsRikrpR7QHRmRH264N17Rs	BEARER	t	t	14
180	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MzQ0Mzg3LCJleHAiOjE2ODk0MzA3ODd9.3dNP_aNCt7cG4aYjDClN5rDsoyv7pW18MM2jn5zE0bg	BEARER	t	t	14
181	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MzYyOTk1LCJleHAiOjE2ODk0NDkzOTV9.RbAjrNLiBAKn5iQih-9IJKsxYeZL6GRCKhSH3NOkR4I	BEARER	t	t	14
182	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MzYzNDE1LCJleHAiOjE2ODk0NDk4MTV9.y59BnuB84TkfGsPxkz8_Cc4t0hy6mUj1x_FBNuCy6sA	BEARER	t	t	14
183	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5NjkyMTA0LCJleHAiOjE2ODk3Nzg1MDR9.VYHSTokfhtX0KMwdZRX46338MxKwySxHFN6g2uhzuaw	BEARER	t	t	14
184	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5NzExMTQxLCJleHAiOjE2ODk3OTc1NDF9.kVEJ9vZ23QP7-AYOlJTuh8dwxN9DMRm_ZNFj7HObSFI	BEARER	t	t	14
185	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5ODYwNzM3LCJleHAiOjE2ODk5NDcxMzd9.0RKvO4MbqbPudDgRnPejAWC6W5vQGS-ef_lexE8IDnY	BEARER	f	f	14
148	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyaXVtYXJpY2hAZ21haWwuY29tIiwiaWF0IjoxNjg5MTkzMTQwLCJleHAiOjE2ODkxOTMxNTJ9.GldzN0RdxIGrFtCMynYnAHjex2wG-11O8qR8qMvPtfo	BEARER	t	t	14
\.


--
-- TOC entry 3753 (class 0 OID 19621)
-- Dependencies: 223
-- Data for Name: user; Type: TABLE DATA; Schema: ukr_gas; Owner: postgres
--

COPY ukr_gas."user" (id, first_name, last_name, email, password, role, creation_date) FROM stdin;
12	Андрій	Марич	example1@gmail.com	$2a$10$IQqqjjrx39S3ZyQ3ueJSrO.9/3U4zUIM4yHi2/YJ1Z1yi2PuPgy/W	USER	2023-07-08
14	Олена	Задорожна	andriumarich@gmail.com	$2a$10$Yah95Q9DnB6onffkjfWaZuK9.wYrDaZBWLweYMMLddXApyEtW0A1m	USER	2023-07-09
\.


--
-- TOC entry 3792 (class 0 OID 0)
-- Dependencies: 231
-- Name: auth_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.auth_id_seq', 1, false);


--
-- TOC entry 3793 (class 0 OID 0)
-- Dependencies: 232
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_id_seq', 1, false);


--
-- TOC entry 3794 (class 0 OID 0)
-- Dependencies: 210
-- Name: account_tariff_id_seq; Type: SEQUENCE SET; Schema: ukr_gas; Owner: postgres
--

SELECT pg_catalog.setval('ukr_gas.account_tariff_id_seq', 1, false);


--
-- TOC entry 3795 (class 0 OID 0)
-- Dependencies: 212
-- Name: address_id_seq; Type: SEQUENCE SET; Schema: ukr_gas; Owner: postgres
--

SELECT pg_catalog.setval('ukr_gas.address_id_seq', 1, false);


--
-- TOC entry 3796 (class 0 OID 0)
-- Dependencies: 234
-- Name: auth_id_seq; Type: SEQUENCE SET; Schema: ukr_gas; Owner: postgres
--

SELECT pg_catalog.setval('ukr_gas.auth_id_seq', 12, true);


--
-- TOC entry 3797 (class 0 OID 0)
-- Dependencies: 215
-- Name: calculation_id_seq; Type: SEQUENCE SET; Schema: ukr_gas; Owner: postgres
--

SELECT pg_catalog.setval('ukr_gas.calculation_id_seq', 3, true);


--
-- TOC entry 3798 (class 0 OID 0)
-- Dependencies: 237
-- Name: currency_rate_id_seq; Type: SEQUENCE SET; Schema: ukr_gas; Owner: postgres
--

SELECT pg_catalog.setval('ukr_gas.currency_rate_id_seq', 36, true);


--
-- TOC entry 3799 (class 0 OID 0)
-- Dependencies: 227
-- Name: feedback_id_seq; Type: SEQUENCE SET; Schema: ukr_gas; Owner: postgres
--

SELECT pg_catalog.setval('ukr_gas.feedback_id_seq', 26, true);


--
-- TOC entry 3800 (class 0 OID 0)
-- Dependencies: 238
-- Name: fuel_price_id_seq; Type: SEQUENCE SET; Schema: ukr_gas; Owner: postgres
--

SELECT pg_catalog.setval('ukr_gas.fuel_price_id_seq', 24, true);


--
-- TOC entry 3801 (class 0 OID 0)
-- Dependencies: 230
-- Name: gas_account_id_seq; Type: SEQUENCE SET; Schema: ukr_gas; Owner: postgres
--

SELECT pg_catalog.setval('ukr_gas.gas_account_id_seq', 2, true);


--
-- TOC entry 3802 (class 0 OID 0)
-- Dependencies: 217
-- Name: meter_reading_id_seq; Type: SEQUENCE SET; Schema: ukr_gas; Owner: postgres
--

SELECT pg_catalog.setval('ukr_gas.meter_reading_id_seq', 39, true);


--
-- TOC entry 3803 (class 0 OID 0)
-- Dependencies: 219
-- Name: payment_id_seq; Type: SEQUENCE SET; Schema: ukr_gas; Owner: postgres
--

SELECT pg_catalog.setval('ukr_gas.payment_id_seq', 23544, true);


--
-- TOC entry 3804 (class 0 OID 0)
-- Dependencies: 221
-- Name: tariff_id_seq; Type: SEQUENCE SET; Schema: ukr_gas; Owner: postgres
--

SELECT pg_catalog.setval('ukr_gas.tariff_id_seq', 1, false);


--
-- TOC entry 3805 (class 0 OID 0)
-- Dependencies: 242
-- Name: telegram_user_id_seq; Type: SEQUENCE SET; Schema: ukr_gas; Owner: postgres
--

SELECT pg_catalog.setval('ukr_gas.telegram_user_id_seq', 13, true);


--
-- TOC entry 3806 (class 0 OID 0)
-- Dependencies: 240
-- Name: token_id_seq; Type: SEQUENCE SET; Schema: ukr_gas; Owner: postgres
--

SELECT pg_catalog.setval('ukr_gas.token_id_seq', 185, true);


--
-- TOC entry 3807 (class 0 OID 0)
-- Dependencies: 233
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: ukr_gas; Owner: postgres
--

SELECT pg_catalog.setval('ukr_gas.user_id_seq', 14, true);


--
-- TOC entry 3545 (class 2606 OID 19889)
-- Name: auth UQ_Auth_Email; Type: CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.auth
    ADD CONSTRAINT "UQ_Auth_Email" UNIQUE (email);


--
-- TOC entry 3567 (class 2606 OID 20196)
-- Name: personal_gas_account UQ_PersonalGasAccount_AccountNumber; Type: CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.personal_gas_account
    ADD CONSTRAINT "UQ_PersonalGasAccount_AccountNumber" UNIQUE (account_number);


--
-- TOC entry 3569 (class 2606 OID 20200)
-- Name: personal_gas_account UQ_PersonalGasAccount_GasMeterNumber; Type: CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.personal_gas_account
    ADD CONSTRAINT "UQ_PersonalGasAccount_GasMeterNumber" UNIQUE (gas_meter_number);


--
-- TOC entry 3579 (class 2606 OID 20204)
-- Name: telegram_user UQ_TelegramUser_ChatId; Type: CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.telegram_user
    ADD CONSTRAINT "UQ_TelegramUser_ChatId" UNIQUE (chat_id);


--
-- TOC entry 3581 (class 2606 OID 20202)
-- Name: telegram_user UQ_TelegramUser_Username; Type: CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.telegram_user
    ADD CONSTRAINT "UQ_TelegramUser_Username" UNIQUE (username);


--
-- TOC entry 3541 (class 2606 OID 19566)
-- Name: account_tariff account_tariff_pkey; Type: CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.account_tariff
    ADD CONSTRAINT account_tariff_pkey PRIMARY KEY (id);


--
-- TOC entry 3543 (class 2606 OID 19575)
-- Name: address address_pkey; Type: CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.address
    ADD CONSTRAINT address_pkey PRIMARY KEY (id);


--
-- TOC entry 3547 (class 2606 OID 19584)
-- Name: auth auth_pkey; Type: CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.auth
    ADD CONSTRAINT auth_pkey PRIMARY KEY (id);


--
-- TOC entry 3549 (class 2606 OID 19591)
-- Name: calculation calculation_pkey; Type: CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.calculation
    ADD CONSTRAINT calculation_pkey PRIMARY KEY (id);


--
-- TOC entry 3561 (class 2606 OID 19766)
-- Name: feedback_category category_pkey; Type: CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.feedback_category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);


--
-- TOC entry 3575 (class 2606 OID 19907)
-- Name: exchange_rate currency_rate_pkey; Type: CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.exchange_rate
    ADD CONSTRAINT currency_rate_pkey PRIMARY KEY (id);


--
-- TOC entry 3559 (class 2606 OID 19683)
-- Name: feedback feedback_pkey; Type: CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.feedback
    ADD CONSTRAINT feedback_pkey PRIMARY KEY (id);


--
-- TOC entry 3563 (class 2606 OID 19777)
-- Name: feedback_type feedback_type_pkey; Type: CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.feedback_type
    ADD CONSTRAINT feedback_type_pkey PRIMARY KEY (id);


--
-- TOC entry 3573 (class 2606 OID 19894)
-- Name: fuel_price fuel_price_pkey; Type: CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.fuel_price
    ADD CONSTRAINT fuel_price_pkey PRIMARY KEY (id);


--
-- TOC entry 3571 (class 2606 OID 19724)
-- Name: personal_gas_account gas_account_pkey; Type: CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.personal_gas_account
    ADD CONSTRAINT gas_account_pkey PRIMARY KEY (id);


--
-- TOC entry 3551 (class 2606 OID 19605)
-- Name: meter_reading meterage_pkey; Type: CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.meter_reading
    ADD CONSTRAINT meterage_pkey PRIMARY KEY (id);


--
-- TOC entry 3553 (class 2606 OID 19612)
-- Name: payment payment_pkey; Type: CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.payment
    ADD CONSTRAINT payment_pkey PRIMARY KEY (id);


--
-- TOC entry 3565 (class 2606 OID 19711)
-- Name: person person_pkey; Type: CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);


--
-- TOC entry 3555 (class 2606 OID 19619)
-- Name: tariff tariff_pkey; Type: CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.tariff
    ADD CONSTRAINT tariff_pkey PRIMARY KEY (id);


--
-- TOC entry 3585 (class 2606 OID 20217)
-- Name: telegram_user_personal_gas_account telegram_user_personal_gas_account_pkey; Type: CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.telegram_user_personal_gas_account
    ADD CONSTRAINT telegram_user_personal_gas_account_pkey PRIMARY KEY (user_id, personal_gas_account_id);


--
-- TOC entry 3583 (class 2606 OID 20191)
-- Name: telegram_user telegram_user_pkey; Type: CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.telegram_user
    ADD CONSTRAINT telegram_user_pkey PRIMARY KEY (id);


--
-- TOC entry 3577 (class 2606 OID 20174)
-- Name: token token_pkey; Type: CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.token
    ADD CONSTRAINT token_pkey PRIMARY KEY (id);


--
-- TOC entry 3557 (class 2606 OID 19628)
-- Name: user user_pkey; Type: CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- TOC entry 3586 (class 2606 OID 19634)
-- Name: account_tariff FK_AccountTariff_TariffId; Type: FK CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.account_tariff
    ADD CONSTRAINT "FK_AccountTariff_TariffId" FOREIGN KEY (tariff_id) REFERENCES ukr_gas.tariff(id);


--
-- TOC entry 3587 (class 2606 OID 19856)
-- Name: calculation FK_Calculation_Personal_gas_account_id; Type: FK CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.calculation
    ADD CONSTRAINT "FK_Calculation_Personal_gas_account_id" FOREIGN KEY (personal_gas_account_id) REFERENCES ukr_gas.personal_gas_account(id) NOT VALID;


--
-- TOC entry 3588 (class 2606 OID 19862)
-- Name: calculation FK_Calculation_Personal_tariff_id; Type: FK CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.calculation
    ADD CONSTRAINT "FK_Calculation_Personal_tariff_id" FOREIGN KEY (tariff_id) REFERENCES ukr_gas.tariff(id) NOT VALID;


--
-- TOC entry 3591 (class 2606 OID 19787)
-- Name: feedback FK_Feedback_CategoryId; Type: FK CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.feedback
    ADD CONSTRAINT "FK_Feedback_CategoryId" FOREIGN KEY (category_id) REFERENCES ukr_gas.feedback_category(id) NOT VALID;


--
-- TOC entry 3592 (class 2606 OID 19798)
-- Name: feedback FK_Feedback_TypeId; Type: FK CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.feedback
    ADD CONSTRAINT "FK_Feedback_TypeId" FOREIGN KEY (type_id) REFERENCES ukr_gas.feedback_type(id) NOT VALID;


--
-- TOC entry 3593 (class 2606 OID 19725)
-- Name: personal_gas_account FK_GasAccount_AddressId; Type: FK CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.personal_gas_account
    ADD CONSTRAINT "FK_GasAccount_AddressId" FOREIGN KEY (address_id) REFERENCES ukr_gas.address(id);


--
-- TOC entry 3594 (class 2606 OID 19730)
-- Name: personal_gas_account FK_GasAccount_PersonId; Type: FK CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.personal_gas_account
    ADD CONSTRAINT "FK_GasAccount_PersonId" FOREIGN KEY (person_id) REFERENCES ukr_gas.person(id);


--
-- TOC entry 3596 (class 2606 OID 19743)
-- Name: personal_gas_account FK_GasAccount_TariffId; Type: FK CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.personal_gas_account
    ADD CONSTRAINT "FK_GasAccount_TariffId" FOREIGN KEY (tariff_id) REFERENCES ukr_gas.account_tariff(id) NOT VALID;


--
-- TOC entry 3595 (class 2606 OID 19735)
-- Name: personal_gas_account FK_GasAccount_UserId; Type: FK CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.personal_gas_account
    ADD CONSTRAINT "FK_GasAccount_UserId" FOREIGN KEY (user_id) REFERENCES ukr_gas."user"(id);


--
-- TOC entry 3589 (class 2606 OID 19813)
-- Name: meter_reading FK_Meterage_Personal_gas_account_id; Type: FK CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.meter_reading
    ADD CONSTRAINT "FK_Meterage_Personal_gas_account_id" FOREIGN KEY (personal_gas_account_id) REFERENCES ukr_gas.personal_gas_account(id) NOT VALID;


--
-- TOC entry 3590 (class 2606 OID 19818)
-- Name: payment FK_Payment_Personal_gas_account_id; Type: FK CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.payment
    ADD CONSTRAINT "FK_Payment_Personal_gas_account_id" FOREIGN KEY (personal_gas_account_id) REFERENCES ukr_gas.personal_gas_account(id) NOT VALID;


--
-- TOC entry 3600 (class 2606 OID 20239)
-- Name: telegram_user_personal_gas_account FK_PersonalGasAccount; Type: FK CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.telegram_user_personal_gas_account
    ADD CONSTRAINT "FK_PersonalGasAccount" FOREIGN KEY (personal_gas_account_id) REFERENCES ukr_gas.personal_gas_account(id) ON DELETE CASCADE NOT VALID;


--
-- TOC entry 3598 (class 2606 OID 20229)
-- Name: telegram_user FK_TelegramUser_CurrentPersonalGasAccount; Type: FK CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.telegram_user
    ADD CONSTRAINT "FK_TelegramUser_CurrentPersonalGasAccount" FOREIGN KEY (current_personal_gas_account_id) REFERENCES ukr_gas.personal_gas_account(id) NOT VALID;


--
-- TOC entry 3597 (class 2606 OID 20176)
-- Name: token FK_Token_UserId; Type: FK CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.token
    ADD CONSTRAINT "FK_Token_UserId" FOREIGN KEY (user_id) REFERENCES ukr_gas."user"(id) NOT VALID;


--
-- TOC entry 3599 (class 2606 OID 20234)
-- Name: telegram_user_personal_gas_account FK_UserId; Type: FK CONSTRAINT; Schema: ukr_gas; Owner: postgres
--

ALTER TABLE ONLY ukr_gas.telegram_user_personal_gas_account
    ADD CONSTRAINT "FK_UserId" FOREIGN KEY (user_id) REFERENCES ukr_gas.telegram_user(id) ON DELETE CASCADE NOT VALID;


-- Completed on 2023-07-22 01:44:28 EEST

--
-- PostgreSQL database dump complete
--

