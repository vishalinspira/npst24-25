DROP TABLE IF EXISTS "qs_comp_forms_2";

CREATE TABLE public.qs_comp_forms_2 (
    id_ bigint NOT NULL,
	pension_fund_code character varying,
	pension_fund_name character varying,
	scheme_name character varying,
	reporting_date date,
	particulars character varying,
	opbal_book_value numeric,
	opbal_market_value numeric,
	purchase_book_value numeric,
	pur_prcnt_of_total_portfolio character varying,
	sales_book_value numeric,
	sales_prcnt_of_total_portfolio character varying,
	adj_book_value numeric,
	adj_market_value numeric,
	adj_prcnt_of_total_portfolio character varying,
	closingbal_market_value numeric,
	closingbal_prcnt_tot_portfolio character varying,
    createdon date,
    createdby bigint,
    reportuploadlogid bigint
) WITH (oids = false);