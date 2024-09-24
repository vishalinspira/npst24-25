DROP TABLE IF EXISTS "qs_comp_forms_5";

CREATE TABLE public.qs_comp_forms_5 (
    id_ bigint NOT NULL,

	pension_fund_code character varying,
	pension_fund_name character varying,
	reporting_date date,
	scheme_name character varying,
	security_name character varying,
	instrument_type character varying,
	interest_rate_prcnt character varying,
	has_there_been_revision character varying,
	total_otstndgbook_value numeric,
	default_principal_bookvalue numeric,
	default_interest_bookvalue numeric,
	principal_due_from  date,
	interest_due_from  date,
	deferred_principal numeric,
	deferred_interest numeric,
	rolled_over character varying,
	principal_waiver_amt numeric,
	prin_waiver_board_apprv_ref character varying,
	classification character varying,
	provision_prcnt character varying,
	provision_amount numeric,
    createdon date,
    createdby bigint,
    reportuploadlogid bigint
) WITH (oids = false);