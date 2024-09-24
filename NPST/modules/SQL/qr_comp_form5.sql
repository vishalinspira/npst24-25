--DROP TABLE IF EXISTS "qr_comp_form5";
--
--CREATE TABLE "public"."qr_comp_form5" (
--    "id_" integer NOT NULL,
--	"pension_fund_code"	character varying,
--	"pension_fund_name"	character varying,
--	"date_"	date,
--	"scheme_name"	character varying,
--	"security_name"	character varying,
--	"instrument_type"	character varying,
--	"interest_rate"	numeric,
--	"has_there_been_revision"	character varying,
--	"total_book_value"	numeric,
--	"default_principal"	numeric,
--	"default_interest"	numeric,
--	"principal_due_from"	date,
--	"interest_due_from"	date,
--	"deferred_principal"	numeric,
--	"deferred_interest"	numeric,
--	"rolled_over"	character varying,
--	"has_there_been_princ_waiver"	numeric,
--	"board_approval_reference"	character varying,
--	"classification"	character varying,
--	"provision_prcnt"	numeric,
--
--
--
--
--    "createdon" date NOT NULL,
--    "createdby" bigint
--) WITH (oids = false);

DROP TABLE IF EXISTS "qr_comp_form5";

CREATE TABLE "public"."qr_comp_form5" (
    "id_" integer NOT NULL,
	"pension_fund_name"	character varying,
	"reporting_date"	date,
	"scheme_name"	character varying,
	"security_name"	character varying,
	"instrument_type"	character varying,
	"interest_rate_prcnt"	character varying,
	"has_there_been_revision"	character varying,
	"total_otstndgbook_value"	numeric,
	"default_principal_bookvalue"	numeric,
	"default_interest_bookvalue"	numeric,
	"principal_due_from"	date,
	"interest_due_from"	date,
	"deferred_principal"	numeric,
	"deferred_interest"	numeric,
	"rolled_over"	character varying,
	"has_there_been_prin_waiver"	numeric,
	"board_approval_reference"	character varying,
	"classification"	character varying,
	"provision_prcnt"	character varying,
	"provision_amount" numeric ,
    "createdon" date NOT NULL,
    "createdby" bigint,
    "reportuploadlogid" bigint
) WITH (oids = false);

