DROP TABLE IF EXISTS "qs_comp_forms_4";

CREATE TABLE public.qs_comp_forms_4 (
    id_ bigint NOT NULL,
	sr_no numeric,
	pension_fund_code character varying,
	pension_fund_name character varying,
	reporting_date date,
	scheme_name character varying,
    market_value_as_on_quarter numeric,
    market_value_as_on_prev_qrtr numeric,
    market_val_as_on_2nd_prev_qrtr numeric, 
    market_val_as_on_3rd_prev_qrtr numeric,
    _3_year_rolling_cagr character varying,
    annualized_portfolio_ret_yield character varying,
    createdon date,
    createdby bigint,
    reportuploadlogid bigint
) WITH (oids = false);