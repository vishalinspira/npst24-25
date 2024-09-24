
--DROP TABLE IF EXISTS "qr_comp_form3";
--
--CREATE TABLE "public"."qr_comp_form3" (
--    "id_" integer NOT NULL,
--	"pension_fund_code"	character varying,
--	"pension_fund_name"	character varying,
--	"scheme_name"	character varying,
--	"date_"	date,
--	"particulars"	character varying,
--	"opening_bal_units"	numeric,
--	"opening_bal_amount"	numeric,
--	"purchase_units"	numeric,
--	"purchase_book_value"	numeric,
--	"pur_prcnt_of_total_portfolio"	numeric,
--	"sales_units"	numeric,
--	"sales_book_value"	numeric,
--	"sales_prcnt_of_total_portfolio"	numeric,
--	"closing_bal_units"	numeric,
--	"closing_bal_book_value"	numeric,
--	"closing_bal_market_value"	numeric,
--	"closingbal_prcnt_total_portfolio"	numeric,
--
--
--
--    "createdon" date NOT NULL,
--    "createdby" bigint
--) WITH (oids = false);


DROP TABLE IF EXISTS "qr_comp_form3";

CREATE TABLE "public"."qr_comp_form3" (
    "id_" integer NOT NULL,
	"pension_fund_name"	character varying,
	"scheme_name"	character varying,
	"reporting_date"	date,
	"particulars"	character varying,
	"opening_bal_units"	numeric,
	"opening_bal_amount"	numeric,
	"purchase_units"	numeric,
	"purchase_book_value"	numeric,
	"pur_prcnt_of_total_portfolio"	character varying,
	"sales_units"	numeric,
	"sales_book_value"	numeric,
	"sales_prcnt_of_total_portfolio"	character varying,
	"closing_bal_units"	numeric,
	"closing_bal_book_value"	numeric,
	"closing_bal_market_value"	numeric,
	"closingbal_prcnt_tot_portfolio"	character varying,
    "createdon" date NOT NULL,
    "createdby" bigint,
    "reportuploadlogid" bigint
    
) WITH (oids = false);