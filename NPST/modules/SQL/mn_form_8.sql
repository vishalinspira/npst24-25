DROP TABLE IF EXISTS "mn_form_8";
DROP SEQUENCE IF EXISTS mn_form_8_id__seq1;
CREATE SEQUENCE mn_form_8_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_form_8 (
    id_ bigint NOT NULL,
	pension_fund_name character varying,
	reporting_date date,
	sl_no int,
	security_name character varying,
	isin_code character varying,
	scheme character varying,
	issuer_name character varying,
	market_value_reporting_date numeric,
	purchase_value numeric,
	date_of_purchase date,
	rating_at_time_purchase character varying,
	rating_agency_time_of_purchase character varying,
	rating_date_of_purchase date,
	first_downgraded_rating character varying,
	date_of_first_downgrade date,
    rating_agency_downgraded character varying,
    current_rating character varying,
    current_rating_agency character varying,
    current_rating_date date,
    createdon date,
    createdby bigint,
     reportuploadlogid bigint,
    CONSTRAINT mn_form_8_pkey PRIMARY KEY (id_)
) WITH (oids = false);