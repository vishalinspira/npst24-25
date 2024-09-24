--
-- PostgreSQL database dump
--

-- Dumped from database version 11.13
-- Dumped by pg_dump version 11.13

-- Started on 2022-05-20 18:40:23

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

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 227 (class 1259 OID 125568)
-- Name: grievancetopfiveentitiesmonth; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.grievancetopfiveentitiesmonth (
    id_ bigint NOT NULL,
    pendingwise character varying(400),
    grievanceoutstanding character varying(400),
    agegroupone character varying(400),
    agegrouptwo character varying(400),
    agegroupthree character varying(400),
    agegroupfour character varying(400),
    agegroupfive character varying(400),
    agegroupsix character varying(400),
    agegroupseven character varying(400),
    wisetype character varying(400)
);


ALTER TABLE public.grievancetopfiveentitiesmonth OWNER TO postgres;

--
-- TOC entry 2899 (class 0 OID 125568)
-- Dependencies: 227
-- Data for Name: grievancetopfiveentitiesmonth; Type: TABLE DATA; Schema: public; Owner: postgres
--


--
-- TOC entry 2777 (class 2606 OID 125575)
-- Name: grievancetopfiveentitiesmonth grievancetopfiveentitiesmonth_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.grievancetopfiveentitiesmonth
    ADD CONSTRAINT grievancetopfiveentitiesmonth_pkey PRIMARY KEY (id_);


-- Completed on 2022-05-20 18:40:25

--
-- PostgreSQL database dump complete
--

