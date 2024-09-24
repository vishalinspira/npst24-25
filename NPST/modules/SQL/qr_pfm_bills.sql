DROP TABLE IF EXISTS "qr_pfm_bills";

CREATE TABLE "public"."qr_pfm_bills" (
    "id_" integer NOT NULL,
	
	"scheme_name"	character varying,
	"transaction_charges_demat_trades"	numeric,
	"custody_charges_demat_holdings"	numeric,
	"nsccl_ccil_charges"	numeric,
	"sebi_charges"	numeric,
	"gross_bill_as_per_pf"	numeric,
	"bill_value_excluding_gst"	numeric,
	"cgst"	numeric,
	"sgst"	numeric,
	"bill_value_including_gst"	numeric,
	"month_" 	date NOT NULL,
	"account_number"	numeric,

    "createdon" date NOT NULL,
    "createdby" bigint
) WITH (oids = false);