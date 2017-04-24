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
    AS $_$SELECT $1 = 'REGISTERED' OR $2 IS NOT NULL$_$;


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
    department_id integer NOT NULL
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
    CONSTRAINT check_performer_permissions CHECK (check_performer_permissions(performer_id, type)),
    CONSTRAINT check_valid_appintment_date CHECK ((appointment_date > (now() - '00:01:00'::interval)))
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
523	medicLoginSample	medicPasswordSample
524	patientLoginSample	patientPasswordSample
1	kopile	1111
2	aaa	wadw
3	www	scasc
4	eee	rrr
5	123	123
6	gthfghrt	sedfdg
7	Nikolay	password
8	azazaz	zazazazaz
9	tyuio	vbn
10	dfbdb	vdfdfs
11	qqqqqqqqqq	wwwwwww
12	lof	las
13	pokojon	6666
14	sdgdsrgm	ijkoiji
15	jklnm,jk	88329jh
16	poool	99091
17	dgdrgg	1123
18	ppol	6666
19	pppopds	90098
20	mamama	omomo
21	doctorLoginSample	doctorPasswordSample
\.


--
-- Name: credentials_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres_user
--

SELECT pg_catalog.setval('credentials_id_seq', 1166, true);


--
-- Data for Name: departments; Type: TABLE DATA; Schema: public; Owner: postgres_user
--

COPY departments (id, name) FROM stdin;
95	department1
96	department2
97	department3
98	department4
\.


--
-- Name: departments_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres_user
--

SELECT pg_catalog.setval('departments_id_seq', 1335, true);


--
-- Data for Name: doctors; Type: TABLE DATA; Schema: public; Owner: postgres_user
--

COPY doctors (stuff_id, credentials_id) FROM stdin;
54	1
55	2
56	3
57	4
58	5
59	6
60	7
61	8
\.


--
-- Data for Name: medics; Type: TABLE DATA; Schema: public; Owner: postgres_user
--

COPY medics (stuff_id, credentials_id) FROM stdin;
62	9
63	10
64	11
65	12
66	13
67	14
68	15
69	16
\.


--
-- Data for Name: patients; Type: TABLE DATA; Schema: public; Owner: postgres_user
--

COPY patients (id, name, surname, doctor_id, complaints, diagnosis, state, credentials_id) FROM stdin;
8	PatientName3	PatientSurname3	\N	Complaints3	Diagnosis3	DISCHARGED	19
6	PatientName1	PatientSurname1	54	Complaints1	Diagnosis1	APPLIED	17
7	PatientName2	PatientSurname2	54	Complaints2	\N	TREATED	18
12	PatientName4	PatientSurname4	55	Complaints4	Diagnosis4	APPLIED	20
\.


--
-- Name: patients_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres_user
--

SELECT pg_catalog.setval('patients_id_seq', 1264, true);


--
-- Data for Name: stuff; Type: TABLE DATA; Schema: public; Owner: postgres_user
--

COPY stuff (id, name, surname, department_id) FROM stdin;
1235	name	surname	95
1236	name	surname	95
1237	name	surname	95
54	DoctorName1	DoctorSurname1	95
55	DoctorName2	DoctorSurname2	96
56	DoctorName3	DoctorSurname3	97
57	DoctorName4	DoctorSurname4	98
58	DoctorName5	DoctorSurname5	95
59	DoctorName6	DoctorSurname6	96
60	DoctorName7	DoctorSurname7	97
63	MedicName2	MedicSurname2	96
64	MedicName3	MedicSurname3	97
65	MedicName4	MedicSurname4	98
66	MedicName5	MedicSurname5	95
67	MedicName6	MedicSurname6	96
68	MedicName7	MedicSurname7	97
69	MedicName8	MedicSurname8	98
61	DoctorName8	DoctorSurname8	98
1241	name	surname	95
1242	name	surname	95
1243	name	surname	95
1698	name	surname	95
62	MedicName1	MedicSurname1	95
\.


--
-- Name: stuff_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres_user
--

SELECT pg_catalog.setval('stuff_id_seq', 2514, true);


--
-- Data for Name: therapies; Type: TABLE DATA; Schema: public; Owner: postgres_user
--

COPY therapies (id, title, type, description, appointment_date, completion_date, patient_id, performer_id) FROM stdin;
17	Title3	SURGERY_OPERATION	Descroption3	2018-05-01 00:01:01	\N	6	57
11	Title1	SURGERY_OPERATION	Description1	2017-04-15 16:55:00	2017-04-16 16:00:00	6	55
45	T8	PHYSIOTHERAPY	d8	2020-01-06 00:00:01	\N	8	69
14	Title2	SURGERY_OPERATION	Description2	2017-04-15 16:55:00	\N	6	54
20	Title4	PHYSIOTHERAPY	Description4	2017-04-15 16:55:00	2017-04-16 16:52:00	8	69
23	Title5	PHARMACOTHERAPY	Description5	2017-04-15 16:55:00	2017-04-15 16:59:00	8	68
29	Title7	PHYSIOTHERAPY	d7	2017-04-15 16:55:01	\N	6	68
28	Title6	SURGERY_OPERATION	d6	2017-04-15 16:55:01	\N	7	55
\.


--
-- Name: therapies_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres_user
--

SELECT pg_catalog.setval('therapies_id_seq', 862, true);


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

