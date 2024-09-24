--
-- PostgreSQL database dump
--

-- Dumped from database version 11.13
-- Dumped by pg_dump version 11.13

-- Started on 2022-05-23 16:39:42

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
-- TOC entry 228 (class 1259 OID 125576)
-- Name: summaryofgrievance; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.summaryofgrievance (
    id_ bigint NOT NULL,
    slno character varying(400),
    entities character varying(400),
    cola character varying(400),
    colb character varying(400),
    colc character varying(400),
    cold character varying(400),
    cole character varying(400),
    colf character varying(400)
);


ALTER TABLE public.summaryofgrievance OWNER TO postgres;

--
-- TOC entry 2919 (class 0 OID 125576)
-- Dependencies: 228
-- Data for Name: summaryofgrievance; Type: TABLE DATA; Schema: public; Owner: postgres
--


--
-- TOC entry 2797 (class 2606 OID 125583)
-- Name: summaryofgrievance summaryofgrievance_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.summaryofgrievance
    ADD CONSTRAINT summaryofgrievance_pkey PRIMARY KEY (id_);


-- Completed on 2022-05-23 16:39:43

--
-- PostgreSQL database dump complete
--

