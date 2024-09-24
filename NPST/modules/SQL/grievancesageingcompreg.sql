--
-- PostgreSQL database dump
--

-- Dumped from database version 11.13
-- Dumped by pg_dump version 11.13

-- Started on 2022-05-23 16:41:43

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
-- TOC entry 232 (class 1259 OID 125608)
-- Name: grievancesageingcompreg; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.grievancesageingcompreg (
    id_ bigint NOT NULL,
    category character varying(400),
    referrals character varying(400),
    agegroupone character varying(400),
    agegrouptwo character varying(400),
    agegroupthree character varying(400),
    agegroupfour character varying(400),
    agegroupfive character varying(400),
    agegroupsix character varying(400),
    agegroupseven character varying(400),
    tabletype character varying(400)
);


ALTER TABLE public.grievancesageingcompreg OWNER TO postgres;

--
-- TOC entry 2919 (class 0 OID 125608)
-- Dependencies: 232
-- Data for Name: grievancesageingcompreg; Type: TABLE DATA; Schema: public; Owner: postgres
--


--
-- TOC entry 2797 (class 2606 OID 125615)
-- Name: grievancesageingcompreg grievancesageingcompreg_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.grievancesageingcompreg
    ADD CONSTRAINT grievancesageingcompreg_pkey PRIMARY KEY (id_);


-- Completed on 2022-05-23 16:41:43

--
-- PostgreSQL database dump complete
--

