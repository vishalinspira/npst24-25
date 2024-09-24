--
-- PostgreSQL database dump
--

-- Dumped from database version 11.13
-- Dumped by pg_dump version 11.13

-- Started on 2022-05-23 16:41:00

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
-- TOC entry 230 (class 1259 OID 125590)
-- Name: comparisonquarterly; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.comparisonquarterly (
    id_ bigint NOT NULL,
    grievances character varying(400),
    qone character varying(400),
    qtwo character varying(400),
    qthree character varying(400),
    qfour character varying(400),
    percentagechange character varying(400)
);


ALTER TABLE public.comparisonquarterly OWNER TO postgres;

--
-- TOC entry 2919 (class 0 OID 125590)
-- Dependencies: 230
-- Data for Name: comparisonquarterly; Type: TABLE DATA; Schema: public; Owner: postgres
--


--
-- TOC entry 2797 (class 2606 OID 125607)
-- Name: comparisonquarterly comparisonquarterly_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comparisonquarterly
    ADD CONSTRAINT comparisonquarterly_pkey PRIMARY KEY (id_);


-- Completed on 2022-05-23 16:41:00

--
-- PostgreSQL database dump complete
--

