--
-- PostgreSQL database dump
--

-- Dumped from database version 11.13
-- Dumped by pg_dump version 11.13

-- Started on 2022-05-23 16:40:05

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
-- TOC entry 229 (class 1259 OID 125584)
-- Name: schemewisecount; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.schemewisecount (
    id_ bigint NOT NULL,
    particulars character varying(400),
    nps character varying(400),
    npsliteandapy character varying(400),
    total character varying(400)
);


ALTER TABLE public.schemewisecount OWNER TO postgres;

--
-- TOC entry 2919 (class 0 OID 125584)
-- Dependencies: 229
-- Data for Name: schemewisecount; Type: TABLE DATA; Schema: public; Owner: postgres
--


--
-- TOC entry 2797 (class 2606 OID 125605)
-- Name: schemewisecount schemewisecount_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.schemewisecount
    ADD CONSTRAINT schemewisecount_pkey PRIMARY KEY (id_);


-- Completed on 2022-05-23 16:40:05

--
-- PostgreSQL database dump complete
--

