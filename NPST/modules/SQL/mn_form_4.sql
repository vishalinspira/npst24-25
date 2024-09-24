DROP TABLE IF EXISTS "mn_form_4_atal_pension_yojana";
DROP SEQUENCE IF EXISTS mn_form_4_atal_pension_yojana_id__seq1;
CREATE SEQUENCE mn_form_4_atal_pension_yojana_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_form_4_atal_pension_yojana (
    id_ bigint NOT NULL,
	pension_fund_name character varying,
	reporting_date date,
	scheme_name character varying,
    code_ bigint,
    description_of_securities character varying,
    amount numeric(20,2),
    percentage_of_portfolio character varying,
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT mn_form_4_atal_pension_yojana_pkey PRIMARY KEY (id_)
) WITH (oids = false);

DROP TABLE IF EXISTS "mn_form_4_corp_cg";
DROP SEQUENCE IF EXISTS mn_form_4_corp_cg_id__seq1;
CREATE SEQUENCE mn_form_4_corp_cg_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_form_4_corp_cg (
    id_ bigint NOT NULL,
	pension_fund_name character varying,
	reporting_date date,
	scheme_name character varying,
    code_ bigint,
    description_of_securities character varying,
    amount numeric(20,2),
    percentage_of_portfolio character varying,
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT mn_form_4_corp_cg_pkey PRIMARY KEY (id_)
) WITH (oids = false);

DROP TABLE IF EXISTS "mn_form_4_nps_lite";
DROP SEQUENCE IF EXISTS mn_form_4_nps_lite_id__seq1;
CREATE SEQUENCE mn_form_4_nps_lite_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_form_4_nps_lite (
    id_ bigint NOT NULL,
	pension_fund_name character varying,
	reporting_date date,
	scheme_name character varying,
    code_ bigint,
    description_of_securities character varying,
    amount numeric(20,2),
    percentage_of_portfolio character varying,
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT mn_form_4_nps_lite_pkey PRIMARY KEY (id_)
) WITH (oids = false);

DROP TABLE IF EXISTS "mn_form_4_cg";
DROP SEQUENCE IF EXISTS mn_form_4_cg_id__seq1;
CREATE SEQUENCE mn_form_4_cg_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_form_4_cg (
    id_ bigint NOT NULL,
	pension_fund_name character varying,
	reporting_date date,
	scheme_name character varying,
    code_ bigint,
    description_of_securities character varying,
    amount numeric(20,2),
    percentage_of_portfolio character varying,
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT mn_form_4_cg_pkey PRIMARY KEY (id_)
) WITH (oids = false);

DROP TABLE IF EXISTS "mn_form_4_sg";
DROP SEQUENCE IF EXISTS mn_form_4_sg_id__seq1;
CREATE SEQUENCE mn_form_4_sg_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_form_4_sg (
    id_ bigint NOT NULL,
	pension_fund_name character varying,
	reporting_date date,
	scheme_name character varying,
    code_ bigint,
    description_of_securities character varying,
    amount numeric(20,2),
    percentage_of_portfolio character varying,
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT mn_form_4_sg_pkey PRIMARY KEY (id_)
) WITH (oids = false);