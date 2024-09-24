DROP TABLE IF EXISTS "qs_comp_forms_1b";

CREATE TABLE public.qs_comp_forms_1b (
    id_ bigint NOT NULL,
	date_ date,
	pension_fund_name character varying,
	scheme_name character varying,
    sno numeric,
    type_purchase_or_sale character varying,
    category_of_investment character varying,
    isin character varying,
    name_of_security_or_company character varying,
    book_value_qtr numeric,
    prcnt_of_portfolio_value_qtr character varying,
    date_of_purchase_or_sale_qtr date,
   
    createdon date,
    createdby bigint,
    reportuploadlogid bigint
) WITH (oids = false);