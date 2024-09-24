DROP TABLE IF EXISTS "qs_comp_forms_3";

CREATE TABLE public.qs_comp_forms_3 (
    id_ bigint NOT NULL,
	
	pension_fund_code character varying,
	pension_fund_name character varying,
	reporting_date date,
	scheme_name character varying,
    particulars character varying,
    opening_bal_units numeric,
    opening_bal_amount numeric,
    purchase_units numeric, 
    purchase_book_value numeric,
    pur_prcnt_of_total_portfolio character varying,
    sales_units numeric,
    sales_book_value numeric,
    sales_prcnt_of_total_portfolio character varying,
    closing_bal_units numeric,
    closing_bal_book_value numeric,
    closing_bal_market_value numeric,
    closingbal_prcnt_tot_portfolio character varying,
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
) WITH (oids = false);