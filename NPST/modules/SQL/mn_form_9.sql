DROP TABLE IF EXISTS "mn_form_9";
DROP SEQUENCE IF EXISTS mn_form_9_id__seq1;
CREATE SEQUENCE mn_form_9_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_form_9 (
    id_ bigint NOT NULL,
	pension_fund_name character varying,
	reporting_date date,
    sl_no int,
	security_name character varying,
	isin_code character varying,
	scheme character varying,
	issuer_name character varying,
	market_value_reporting_date numeric(20,2),
	purchase_value numeric(20,2),
	date_of_purchase date,
    face_value numeric(20,2),
	npa_since date,
	recoveries_till_reporting_date numeric(20,2),
	book_value_net_recovery_date numeric(20,2),
	provision_held_against_book numeric(20,2),
	interest_default_till_npa_date numeric,
	intrst_default_after_npa_date numeric,
	interest_default_total numeric,
	provision_held_against_int numeric(20,2),
	remarks character varying,
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT mn_form_9_pkey PRIMARY KEY (id_)
) WITH (oids = false);