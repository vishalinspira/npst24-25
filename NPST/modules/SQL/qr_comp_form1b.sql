--DROP TABLE IF EXISTS "qr_comp_form1b";
--
--CREATE TABLE "public"."qr_comp_form1b" (
--    "id_" integer NOT NULL,
--	"date_"	date,
--	"pfm_code"	character varying,
--	"name_of_pfm"	character varying,
--	"scheme_name"	character varying,
--	"form_name"	character varying,
--	"s_no" character varying(3),
--	"type"	character varying,
--	"category_of_investment"	character varying,
--	"isin"	character varying(12),
--	"name_of_security_or_company"	character varying,
--	"book_value_qtr"	numeric,
--	"prcnt_of_portfolio_value_qtr"	numeric,
--	"date_of_purchase_or_sale_qtr"	date,
--	"name_"	character varying,
--	"designation"	character varying,
--	"transaction_in_the_name_of"	character varying,
--
--    "createdon" date NOT NULL,
--    "createdby" bigint
--) WITH (oids = false);

DROP TABLE IF EXISTS "qr_comp_form_1b";

CREATE TABLE "public"."qr_comp_form_1b" (
    "id_" integer NOT NULL,
	"reporting_date" date,
	"pension_fund_name" character varying,
	"scheme_name" character varying,
	"sno" numeric,
	"type_purchase_or_sale"	character varying,
	"category_of_investment" character varying,
	"isin"	character varying,
	"name_of_security_or_company" character varying,
	"book_value_qtr" numeric,
	"prcnt_of_portfolio_value" character varying,
	"date_of_purchase_or_sale_qtr" date,
    "createdon" date,
    "createdby" bigint,
    "reportuploadlogid" bigint
) WITH (oids = false);