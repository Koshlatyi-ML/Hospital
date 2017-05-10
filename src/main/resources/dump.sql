--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.6
-- Dumped by pg_dump version 9.5.6

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- Name: check_compliants_logic(character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres_user
--

CREATE FUNCTION check_compliants_logic(state character varying, complaints character varying) RETURNS boolean
    LANGUAGE sql
    AS $_$SELECT $1 IN ('REGISTERED','DISCHARGED') OR $2 IS NOT NULL$_$;


ALTER FUNCTION public.check_compliants_logic(state character varying, complaints character varying) OWNER TO postgres_user;

--
-- Name: check_discharged_state_logic(character varying, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres_user
--

CREATE FUNCTION check_discharged_state_logic(state character varying, diagnosis character varying, doctor_id integer) RETURNS boolean
    LANGUAGE sql
    AS $_$SELECT $1 <> 'DISCHARGED' OR ($2 IS NOT NULL AND $3 IS NULL)$_$;


ALTER FUNCTION public.check_discharged_state_logic(state character varying, diagnosis character varying, doctor_id integer) OWNER TO postgres_user;

--
-- Name: check_performer_permissions(integer, character varying); Type: FUNCTION; Schema: public; Owner: postgres_user
--

CREATE FUNCTION check_performer_permissions(id integer, type character varying) RETURNS boolean
    LANGUAGE sql
    AS $_$select $2 <> 'SURGERY_OPERATION' OR $1 in (select stuff_id from doctors)$_$;


ALTER FUNCTION public.check_performer_permissions(id integer, type character varying) OWNER TO postgres_user;

--
-- Name: valid_owner(smallint); Type: FUNCTION; Schema: public; Owner: postgres_user
--

CREATE FUNCTION valid_owner(owner_id smallint) RETURNS boolean
    LANGUAGE sql
    AS $_$select $1 in (1,2,3)$_$;


ALTER FUNCTION public.valid_owner(owner_id smallint) OWNER TO postgres_user;

--
-- Name: valid_state(character varying); Type: FUNCTION; Schema: public; Owner: postgres_user
--

CREATE FUNCTION valid_state(type character varying) RETURNS boolean
    LANGUAGE sql
    AS $_$select $1 in ('REGISTERED', 'APPLIED', 'TREATED', 'DISCHARGED') $_$;


ALTER FUNCTION public.valid_state(type character varying) OWNER TO postgres_user;

--
-- Name: valid_type(character varying); Type: FUNCTION; Schema: public; Owner: postgres_user
--

CREATE FUNCTION valid_type(type character varying) RETURNS boolean
    LANGUAGE sql
    AS $_$select $1 in ('SURGERY_OPERATION', 'PHARMACOTHERAPY', 'PHYSIOTHERAPY') $_$;


ALTER FUNCTION public.valid_type(type character varying) OWNER TO postgres_user;

--
-- Name: credentials_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres_user
--

CREATE SEQUENCE credentials_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE credentials_id_seq OWNER TO postgres_user;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: credentials; Type: TABLE; Schema: public; Owner: postgres_user
--

CREATE TABLE credentials (
    id bigint DEFAULT nextval('credentials_id_seq'::regclass) NOT NULL,
    login character varying(255) NOT NULL,
    password character varying(255) NOT NULL
);


ALTER TABLE credentials OWNER TO postgres_user;

--
-- Name: departments_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres_user
--

CREATE SEQUENCE departments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE departments_id_seq OWNER TO postgres_user;

--
-- Name: departments; Type: TABLE; Schema: public; Owner: postgres_user
--

CREATE TABLE departments (
    id smallint DEFAULT nextval('departments_id_seq'::regclass) NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE departments OWNER TO postgres_user;

--
-- Name: doctors; Type: TABLE; Schema: public; Owner: postgres_user
--

CREATE TABLE doctors (
    stuff_id integer NOT NULL,
    credentials_id bigint NOT NULL
);


ALTER TABLE doctors OWNER TO postgres_user;

--
-- Name: medics; Type: TABLE; Schema: public; Owner: postgres_user
--

CREATE TABLE medics (
    stuff_id integer NOT NULL,
    credentials_id bigint NOT NULL
);


ALTER TABLE medics OWNER TO postgres_user;

--
-- Name: patients; Type: TABLE; Schema: public; Owner: postgres_user
--

CREATE TABLE patients (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    surname character varying(255) NOT NULL,
    doctor_id integer,
    complaints character varying(511),
    diagnosis character varying(255),
    state character varying(255) NOT NULL,
    credentials_id bigint NOT NULL,
    CONSTRAINT check_complaint_logic CHECK (check_compliants_logic(state, complaints)),
    CONSTRAINT check_state_logic CHECK (check_discharged_state_logic(state, diagnosis, doctor_id)),
    CONSTRAINT check_valid_state CHECK (valid_state(state))
);


ALTER TABLE patients OWNER TO postgres_user;

--
-- Name: patients_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres_user
--

CREATE SEQUENCE patients_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE patients_id_seq OWNER TO postgres_user;

--
-- Name: patients_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres_user
--

ALTER SEQUENCE patients_id_seq OWNED BY patients.id;


--
-- Name: stuff; Type: TABLE; Schema: public; Owner: postgres_user
--

CREATE TABLE stuff (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    surname character varying(255) NOT NULL,
    department_id integer
);


ALTER TABLE stuff OWNER TO postgres_user;

--
-- Name: stuff_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres_user
--

CREATE SEQUENCE stuff_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE stuff_id_seq OWNER TO postgres_user;

--
-- Name: stuff_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres_user
--

ALTER SEQUENCE stuff_id_seq OWNED BY stuff.id;


--
-- Name: therapies; Type: TABLE; Schema: public; Owner: postgres_user
--

CREATE TABLE therapies (
    id bigint NOT NULL,
    title character varying(255) NOT NULL,
    type character varying(255) NOT NULL,
    description character varying(255) NOT NULL,
    appointment_date timestamp without time zone NOT NULL,
    completion_date timestamp without time zone,
    patient_id bigint NOT NULL,
    performer_id integer NOT NULL,
    CONSTRAINT check_complete_date CHECK ((completion_date > appointment_date)),
    CONSTRAINT check_is_valid_type CHECK (valid_type(type)),
    CONSTRAINT check_performer_permissions CHECK (check_performer_permissions(performer_id, type))
);


ALTER TABLE therapies OWNER TO postgres_user;

--
-- Name: therapies_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres_user
--

CREATE SEQUENCE therapies_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE therapies_id_seq OWNER TO postgres_user;

--
-- Name: therapies_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres_user
--

ALTER SEQUENCE therapies_id_seq OWNED BY therapies.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres_user
--

ALTER TABLE ONLY patients ALTER COLUMN id SET DEFAULT nextval('patients_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres_user
--

ALTER TABLE ONLY stuff ALTER COLUMN id SET DEFAULT nextval('stuff_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres_user
--

ALTER TABLE ONLY therapies ALTER COLUMN id SET DEFAULT nextval('therapies_id_seq'::regclass);


--
-- Data for Name: credentials; Type: TABLE DATA; Schema: public; Owner: postgres_user
--

COPY credentials (id, login, password) FROM stdin;
1	House_MD	Vol4anka
3	MYKOLALALA	12341234Aa
5	House_MDD	12345678aA
6	House_MDMA	Vol4anka
10	House_MDqww	Vol4anka
13	log	pswd
14	loooooooooo	Vol4anka
15	DoctorLogin	Password1111
16	LoginLOgin	Passwrodff546
17	sdgfdbdtfbdfg	2352524sdvvA
18	Looooooooooosdsd	Lsdgdfbdfgd235
19	olololololo	MyPassword1234
20	Lofbdbddfdf	Lonndfnfdfn23
21	MedicLogin	MedicLogin1111
22	medicLogin	MedicPassword1234
24	JustNewMedicLogin	JustNewMedicPsd1234
25	Loginlalala	lalalapWd1234
26	medicmdic	MedicMedic1234
27	MedicLogin13	PasswordGreat13
28	medsestra123	Zazazaza12
31	Loginnigol	Password1234
32	superlogin	Epam1234
33	qwerty12345	Qwerty1234
37	MykolaKoshlatyi96	MykolaKoshlatyi96
38	LoginSample	LoginSample1
39	cxfbdfxbdxbdxdfbvx	DocGVHvbH124
40	vladikNikolaev	vladikNikolaev123
\.


--
-- Name: credentials_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres_user
--

SELECT pg_catalog.setval('credentials_id_seq', 40, true);


--
-- Data for Name: departments; Type: TABLE DATA; Schema: public; Owner: postgres_user
--

COPY departments (id, name) FROM stdin;
2	Neurology
3	Oncology
4	Dermatology
5	Psychiatric
9	Cardiology
17	Traumatology
\.


--
-- Name: departments_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres_user
--

SELECT pg_catalog.setval('departments_id_seq', 18, true);


--
-- Data for Name: doctors; Type: TABLE DATA; Schema: public; Owner: postgres_user
--

COPY doctors (stuff_id, credentials_id) FROM stdin;
1	1
2	3
3	5
4	6
6	15
8	17
9	18
10	21
11	22
12	24
13	25
14	26
19	39
7	16
\.


--
-- Data for Name: medics; Type: TABLE DATA; Schema: public; Owner: postgres_user
--

COPY medics (stuff_id, credentials_id) FROM stdin;
5	10
16	28
15	27
18	33
17	32
\.


--
-- Data for Name: patients; Type: TABLE DATA; Schema: public; Owner: postgres_user
--

COPY patients (id, name, surname, doctor_id, complaints, diagnosis, state, credentials_id) FROM stdin;
12	nhb	kjknj	\N	\N	\N	REGISTERED	20
11	Микола	Вересень	\N	\N	\N	REGISTERED	19
17	Владик	Николаев	1	Rigid pain in stomach.	\N	APPLIED	40
13	Николай	Леоненко	\N	\N	DIAGNOSIS	DISCHARGED	31
16	Имя	Фамилия	1	complications	DIAGNOSIS	APPLIED	38
10	a	b	6	Кашель, боль в горле.	\N	APPLIED	14
15	Николай	Кошлатый-Михно	\N	\N	Воспаление хитрости	DISCHARGED	37
\.


--
-- Name: patients_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres_user
--

SELECT pg_catalog.setval('patients_id_seq', 17, true);


--
-- Data for Name: stuff; Type: TABLE DATA; Schema: public; Owner: postgres_user
--

COPY stuff (id, name, surname, department_id) FROM stdin;
1	Gregory	House	3
2	Mykola	Koshlatyi	5
6	Николай	Кошлатый	3
11	Ольга	Кононенко	3
12	Виктория	Прусик	3
13	Екатерина	Онегина	3
16	Елена	Пирогова	3
3	Антон	Захаров	2
4	Максим	Нестерук	5
5	Анна	Артеменко	3
8	Григорий	Борисов	2
9	Артур	Прокопчук	2
10	Инна	Викторчук	4
14	Борислав	Осокин	3
19	SDGS	SDRGSG	2
15	Ольга	Майстренко	3
18	Олег	Борисенко	4
17	Елена	Егорова	17
7	Omar	Epps	2
\.


--
-- Name: stuff_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres_user
--

SELECT pg_catalog.setval('stuff_id_seq', 19, true);


--
-- Data for Name: therapies; Type: TABLE DATA; Schema: public; Owner: postgres_user
--

COPY therapies (id, title, type, description, appointment_date, completion_date, patient_id, performer_id) FROM stdin;
6	Шунтирование сердца	SURGERY_OPERATION	Кто-то перепутал назначения.	2017-04-24 04:37:00	2017-04-24 06:32:10.908	13	6
5	Тубус кварц	PHYSIOTHERAPY	Тубус кварц 5 минут.	2017-04-24 02:13:00	2017-04-24 07:11:48.517	10	15
7	Сироп от кашля	PHARMACOTHERAPY	Пить 3 раза в день после еды. Курс лечения - 7 дней.	2017-04-24 06:18:00	2017-04-24 07:17:21.619	10	15
11	Викодинотерапия	PHARMACOTHERAPY	Пить викодин при каждой удобной возможности.	2017-04-24 08:26:00	2017-04-24 08:28:22.762	15	15
12	Пункция	SURGERY_OPERATION	Ну куда Доктору Хаусу без неё.	2017-04-24 08:31:00	2017-04-24 08:31:29.83	15	1
13	Тубус кварц	PHARMACOTHERAPY	Тубус кварц 5 минут.	2017-04-24 16:18:00	2017-04-24 16:19:36.179	16	15
14	cxbxfbfd	PHARMACOTHERAPY	dfbdbdfbdxb	2017-04-24 16:21:00	2017-04-24 16:21:35.474	16	1
15	Подорожникотерапия	PHARMACOTHERAPY	Приожить подорожник	2017-04-28 05:35:00	2017-04-28 05:35:29.454	13	15
25	sfer	PHARMACOTHERAPY	SDFSF	2017-04-28 05:55:47.091	2018-04-28 05:55:48.029	13	15
16	м рпаогш	PHARMACOTHERAPY	погонпп	2017-04-28 05:37:00	2017-04-28 06:12:44.689	13	1
26	fgdfhgdg	PHARMACOTHERAPY	fdghfdhdfh	2017-04-28 06:25:00	2017-04-28 06:27:50.84	13	1
27	пришить	PHYSIOTHERAPY	что еще	2017-04-28 06:31:00	2017-04-28 06:31:04.642	13	15
28	Therapy	SURGERY_OPERATION	implant brains	2017-04-28 06:35:00	2017-04-28 06:35:20.435	13	1
29	sdsgsgsgsg	PHARMACOTHERAPY	sdgsgsgs	2017-04-28 06:43:00	2017-04-28 06:49:37.165	13	1
30	therapy	PHYSIOTHERAPY	descriotion	2017-04-28 17:23:00	2017-04-28 17:23:47.905	16	15
31	Operation	SURGERY_OPERATION	cut the sarcoma	2017-04-28 17:24:00	2017-04-28 17:24:21.877	13	1
\.


--
-- Name: therapies_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres_user
--

SELECT pg_catalog.setval('therapies_id_seq', 31, true);


--
-- Name: credentials_login_key; Type: CONSTRAINT; Schema: public; Owner: postgres_user
--

ALTER TABLE ONLY credentials
    ADD CONSTRAINT credentials_login_key UNIQUE (login);


--
-- Name: credentials_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres_user
--

ALTER TABLE ONLY credentials
    ADD CONSTRAINT credentials_pkey PRIMARY KEY (id);


--
-- Name: departments_name_unique; Type: CONSTRAINT; Schema: public; Owner: postgres_user
--

ALTER TABLE ONLY departments
    ADD CONSTRAINT departments_name_unique UNIQUE (name);


--
-- Name: departments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres_user
--

ALTER TABLE ONLY departments
    ADD CONSTRAINT departments_pkey PRIMARY KEY (id);


--
-- Name: doctors_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres_user
--

ALTER TABLE ONLY doctors
    ADD CONSTRAINT doctors_pkey PRIMARY KEY (stuff_id);


--
-- Name: medics_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres_user
--

ALTER TABLE ONLY medics
    ADD CONSTRAINT medics_pkey PRIMARY KEY (stuff_id);


--
-- Name: patients_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres_user
--

ALTER TABLE ONLY patients
    ADD CONSTRAINT patients_pkey PRIMARY KEY (id);


--
-- Name: stuff_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres_user
--

ALTER TABLE ONLY stuff
    ADD CONSTRAINT stuff_pkey PRIMARY KEY (id);


--
-- Name: therapies_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres_user
--

ALTER TABLE ONLY therapies
    ADD CONSTRAINT therapies_pkey PRIMARY KEY (id);


--
-- Name: unique_credentials_id; Type: CONSTRAINT; Schema: public; Owner: postgres_user
--

ALTER TABLE ONLY doctors
    ADD CONSTRAINT unique_credentials_id UNIQUE (credentials_id);


--
-- Name: unique_medic_credentials_id; Type: CONSTRAINT; Schema: public; Owner: postgres_user
--

ALTER TABLE ONLY medics
    ADD CONSTRAINT unique_medic_credentials_id UNIQUE (credentials_id);


--
-- Name: unique_patients_credentials_id; Type: CONSTRAINT; Schema: public; Owner: postgres_user
--

ALTER TABLE ONLY patients
    ADD CONSTRAINT unique_patients_credentials_id UNIQUE (credentials_id);


--
-- Name: doctors_credentials_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres_user
--

ALTER TABLE ONLY doctors
    ADD CONSTRAINT doctors_credentials_id_fkey FOREIGN KEY (credentials_id) REFERENCES credentials(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: doctors_stuffs_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres_user
--

ALTER TABLE ONLY doctors
    ADD CONSTRAINT doctors_stuffs_fk FOREIGN KEY (stuff_id) REFERENCES stuff(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: medics_credentials_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres_user
--

ALTER TABLE ONLY medics
    ADD CONSTRAINT medics_credentials_id_fkey FOREIGN KEY (credentials_id) REFERENCES credentials(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: medics_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres_user
--

ALTER TABLE ONLY medics
    ADD CONSTRAINT medics_id_fkey FOREIGN KEY (stuff_id) REFERENCES stuff(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: patients_credentials_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres_user
--

ALTER TABLE ONLY patients
    ADD CONSTRAINT patients_credentials_id_fkey FOREIGN KEY (credentials_id) REFERENCES credentials(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: patients_doctor_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres_user
--

ALTER TABLE ONLY patients
    ADD CONSTRAINT patients_doctor_id_fkey FOREIGN KEY (doctor_id) REFERENCES doctors(stuff_id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: stuff_department_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres_user
--

ALTER TABLE ONLY stuff
    ADD CONSTRAINT stuff_department_id_fkey FOREIGN KEY (department_id) REFERENCES departments(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: therapies__patient_id_fkey1; Type: FK CONSTRAINT; Schema: public; Owner: postgres_user
--

ALTER TABLE ONLY therapies
    ADD CONSTRAINT therapies__patient_id_fkey1 FOREIGN KEY (patient_id) REFERENCES patients(id);


--
-- Name: therapies__performer_id_fkey1; Type: FK CONSTRAINT; Schema: public; Owner: postgres_user
--

ALTER TABLE ONLY therapies
    ADD CONSTRAINT therapies__performer_id_fkey1 FOREIGN KEY (performer_id) REFERENCES stuff(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

