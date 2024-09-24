DROP TABLE IF EXISTS "qs_comp_cashflow";

CREATE TABLE public.qs_comp_cashflow (
    id_ bigint NOT NULL,
	pension_fund_manager_name_code character varying,
    scheme_name character varying,
    periodicity_of_submission character varying,
    statement_as_on date,
    opening_balance_market_value numeric,
    inflow_during_the_quarter numeric,
    inc_dec_val_of_inv_net numeric,
    outflow_during_the_quarter numeric,
    total_investible_fund_mkt_val character varying,
    createdon date,
    createdby bigint,
    reportuploadlogid bigint
) WITH (oids = false);