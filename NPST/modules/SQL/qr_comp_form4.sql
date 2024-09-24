--DROP TABLE IF EXISTS "qr_comp_form4";
--
--CREATE TABLE "public"."qr_comp_form4" (
--    "id_" integer NOT NULL,
--	"sr_no"	numeric,
--	"pension_fund_code"	character varying,
--	"pension_fund_name"	character varying,
--	"date_"	date,
--	"scheme_name"	character varying,
--	"mkt_value_as_on_qrtr" 	numeric,
--	"mkt_value_as_on_prev_qrtr" 	numeric,
--	"mkt_value_as_on_2nd_prev_qrtr" 	numeric,
--	"mkt_value_as_on_3rd_prev_qrtr" 	numeric,
--	"_3_year_rolling_cagr"	numeric,
--	"annualized_portfolio_return" 	numeric,
--
--
--
--
--
--    "createdon" date NOT NULL,
--    "createdby" bigint
--) WITH (oids = false);

DROP TABLE IF EXISTS "qr_comp_form4";

CREATE TABLE "public"."qr_comp_form4" (
    "id_" integer NOT NULL,
	"sr_no"	numeric,
	"pension_fund_name"	character varying,
	"reporting_date"	date,
	"scheme_name"	character varying,
	"market_value_as_on_quarter" 	numeric,
	"market_value_as_on_prev_qrtr" 	numeric,
	"market_val_as_on_2nd_prev_qrtr" 	numeric,
	"market_val_as_on_3rd_prev_qrtr" 	numeric,
	"_3_year_rolling_cagr"	numeric,
	"annualized_portfolio_ret_yield" 	numeric,
    "createdon" date NOT NULL,
    "createdby" bigint,
    "reportuploadlogid" bigint
) WITH (oids = false);

