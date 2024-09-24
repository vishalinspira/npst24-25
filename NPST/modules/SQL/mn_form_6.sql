DROP TABLE IF EXISTS "mn_form_6_e_i";
DROP SEQUENCE IF EXISTS mn_form_6_e_i_sg_id__seq1;
CREATE SEQUENCE mn_form_6_e_i_sg_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_form_6_e_i (
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
    CONSTRAINT mn_form_6_e_i_sg_pkey PRIMARY KEY (id_)
) WITH (oids = false);

DROP TABLE IF EXISTS "mn_form_6_e_ii";
DROP SEQUENCE IF EXISTS mn_form_6_e_ii_sg_id__seq1;
CREATE SEQUENCE mn_form_6_e_ii_sg_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_form_6_e_ii (
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
    CONSTRAINT mn_form_6_e_ii_sg_pkey PRIMARY KEY (id_)
) WITH (oids = false);