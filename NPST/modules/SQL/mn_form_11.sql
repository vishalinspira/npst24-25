DROP TABLE IF EXISTS "mn_form_11_sponsor";
DROP SEQUENCE IF EXISTS mn_form_11_sponsor_id__seq1;
CREATE SEQUENCE mn_form_11_sponsor_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_form_11_sponsor (
    id_ bigint NOT NULL,
	pension_fund_name character varying,
	reporting_date date,
    scheme_name character varying,
	name_of_group character varying,
	investee_company character varying,
	eq_perm_limit_of_paidupcapital numeric(20,2),
	eq_permissible_limit_of_aum numeric(20,2),
	eq_actual_investment numeric(20,2),
	eq_deviation_percentage character varying,
	debt_limit_of_networth numeric(20,2),
	debt_limit_of_aum numeric(20,2),
	debt_actual_investment numeric(20,2),
	debt_deviation_percentage character varying,
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT mn_form_11_sponsor_pkey PRIMARY KEY (id_)
) WITH (oids = false);

DROP TABLE IF EXISTS "mn_form_11_non_sponsor";
DROP SEQUENCE IF EXISTS mn_form_11_non_sponsor_id__seq1;
CREATE SEQUENCE mn_form_11_non_sponsor_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_form_11_non_sponsor (
    id_ bigint NOT NULL,
	pension_fund_name character varying,
	reporting_date date,
    scheme_name character varying,
	name_of_group character varying,
	investee_company character varying,
	eq_perm_limit_of_paidupcapital numeric(20,2),
	eq_permissible_limit_of_aum numeric(20,2),
	eq_actual_investment numeric(20,2),
	eq_deviation_percentage character varying,
	debt_limit_of_networth numeric(20,2),
	debt_limit_of_aum numeric(20,2),
	debt_actual_investment numeric(20,2),
	debt_deviation_percentage character varying,
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT mn_form_11_non_sponsor_pkey PRIMARY KEY (id_)
) WITH (oids = false);





