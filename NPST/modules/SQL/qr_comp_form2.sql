--DROP TABLE IF EXISTS "qr_comp_form2";
--
--CREATE TABLE "public"."qr_comp_form2" (
--    "id_" integer NOT NULL,
--	"pension_fund_code"	character varying,
--	"pension_fund_name"	character varying,
--	"scheme_name"	character varying,
--	"date_"	date,
--	"particulars"	character varying,
--	"book_value"	numeric,
--	"market_value"	numeric,
--	"purchase_book_value"	numeric,
--	"pur_prcnt_of_tot_port"	numeric,
--	"sales_book_value"	numeric,
--	"sales_prcnt_of_tot_port"	numeric,
--	"adj_book_value"	numeric,
--	"adj_prcnt_of_tot_port"	numeric,
--	"closingbal_market_value"	numeric,
--	"closingbal_prcnt_tot_port"	numeric,
--
--
--    "createdon" date NOT NULL,
--    "createdby" bigint
--) WITH (oids = false);

DROP TABLE IF EXISTS "qr_comp_form2";

CREATE TABLE "public"."qr_comp_form2" (
    "id_" integer NOT NULL,
	"pension_fund_name"	character varying,
	"scheme_name"	character varying,
	"reporting_date"	date,
	"particulars"	character varying,
	"opbal_book_value"	numeric,
	"opbal_market_value"	numeric,
	"purchase_book_value"	numeric,
	"pur_prcnt_of_total_portfolio"	character varying,
	"sales_book_value"	numeric,
	"sales_prcnt_of_total_portfolio"	character varying,
	"adj_book_value"	numeric,
	"adj_market_value" numeric,
	"adj_prcnt_of_total_portfolio" character varying,
	"closingbal_market_value"	numeric,
	"closingbal_book_value" numeric ,
	"closingbal_prcnt_tot_portfolio"	character varying,
    "createdon" date NOT NULL,
    "createdby" bigint,
    "reportuploadlogid" bigint
) WITH (oids = false);

