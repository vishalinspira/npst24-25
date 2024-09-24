DROP TABLE IF EXISTS "qr_comp_form_1a";
DROP SEQUENCE IF EXISTS qr_comp_form_1a_id__seq1;
CREATE SEQUENCE qr_comp_form_1a_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.qr_comp_form_1a (
    id_ bigint NOT NULL,
	reporting_date date,
	pension_fund_name character varying,
    sno numeric,
    type_purchase_or_sale character varying,
    category_of_investment character varying,
    isin character varying,
    name_of_security_or_company character varying,
    book_value_qtr numeric,
    prcnt_of_portfolio_value_qtr character varying,
    date_of_purchase_or_sale_qtr date,
    name_ character varying,
    designation character varying,
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT qr_comp_form_1a_pkey PRIMARY KEY (id_)
) WITH (oids = false);