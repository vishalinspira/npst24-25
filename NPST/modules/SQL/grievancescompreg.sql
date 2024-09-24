--
-- PostgreSQL database dump
--

-- Dumped from database version 11.13
-- Dumped by pg_dump version 11.13

-- Started on 2022-05-23 16:41:24

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
-- TOC entry 231 (class 1259 OID 125596)
-- Name: grievancescompreg; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.grievancescompreg (
    id_ bigint NOT NULL,
    category character varying(400),
    qone character varying(400),
    qtwo character varying(400),
    qthree character varying(400),
    qfour character varying(400),
    tabletype character varying(400)
);


ALTER TABLE public.grievancescompreg OWNER TO postgres;

--
-- TOC entry 2919 (class 0 OID 125596)
-- Dependencies: 231
-- Data for Name: grievancescompreg; Type: TABLE DATA; Schema: public; Owner: postgres
--


--
-- TOC entry 2797 (class 2606 OID 125603)
-- Name: grievancescompreg grievancescompreg_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.grievancescompreg
    ADD CONSTRAINT grievancescompreg_pkey PRIMARY KEY (id_);


-- Completed on 2022-05-23 16:41:25

--
-- PostgreSQL database dump complete
--

