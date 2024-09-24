DROP TABLE IF EXISTS "mn_form_10";
DROP SEQUENCE IF EXISTS mn_form_10_id__seq1;
CREATE SEQUENCE mn_form_10_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_form_10 (
    id_ bigint NOT NULL,
	pension_fund_name character varying,
	reporting_date date,
    scheme_name character varying,
	nic_code character varying,
	nic_industry_desc character varying,
	permissible_limit_of_industry numeric(20,2),
	actual_investments numeric(20,2),
	deviation numeric(20,2),
	percentage_of_deviation character varying,
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT mn_form_10_pkey PRIMARY KEY (id_)
) WITH (oids = false);