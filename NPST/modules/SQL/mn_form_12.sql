DROP TABLE IF EXISTS "mn_form_12";
DROP SEQUENCE IF EXISTS mn_form_12_id__seq1;
CREATE SEQUENCE mn_form_12_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_form_12 (
    id_ bigint NOT NULL,
	pension_fund_name character varying,
	reporting_date date,
    scheme_name character varying,
	name_of_the_issuer character varying,
	equity_perm_limit_aum_prcnt character varying,
	equity_perm_limit_aum_amount numeric(20,2),
	equity_actual_investment numeric(20,2),
	equity_deviation_percentage character varying,
	equity_deviation_amount numeric(20,2),
	debt_perm_limit_aum_percentage character varying,
	debt_perm_limit_of_aum_amount numeric(20,2),
	debt_actual_investment numeric(20,2),
	debt_deviation_percentage character varying,
	debt_deviation_amount numeric(20,2),
	details_of_exposure_exceptions character varying,
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT mn_form_12_pkey PRIMARY KEY (id_)
) WITH (oids = false);