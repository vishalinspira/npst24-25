DROP TABLE IF EXISTS "qr_report";
DROP SEQUENCE IF EXISTS qr_report_id__seq1;
CREATE SEQUENCE qr_report_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.qr_report (
    id_ integer NOT NULL,
    	"pension_fund_name"	character varying,
	"as_on_date"	date,
	"asset_class"	character varying,
	"asset_sub_class"	character varying,
	"cg"	numeric,
	"percentage_cg"		character varying,
	"sg"	numeric,
	"percentage_sg"		character varying,
	"e_i"	numeric,
	"percentage_e_i"		character varying,
	"c_i"	numeric,
	"percentage_c_i"		character varying,
	"g_i"	numeric,
	"percentage_g_i"		character varying,
	"e_ii"	numeric,
	"percentage_e_ii"		character varying,
	"c_ii"	numeric,
	"percentage_c_ii"		character varying,
	"g_ii"	numeric,
	"percentage_g_ii"		character varying,
	"nps_lite"	numeric,
	"percentage_nps_lite"		character varying,
	"cor_cg"	numeric,
	"percentage_cor_cg"		character varying,
	"purchases_and_sales"	numeric,
	"amount_received_or_accrued"	numeric,
	"group_of_the_sponsor_or_pfm"	character varying,
	"down_graded_investments"	character varying,
	"apy"	numeric,
	"percentage_apy"		character varying,
	"a_i"	numeric,
	"percentage_a_i"		character varying,
	"scheme_nps_tts_ii"	numeric,
	"percentage_scheme_nps_tts_ii"		character varying,
	"total_aum"	numeric,
	"percentage"		character varying,
    "createdate" date,
    "createdby" integer,
    "reportuploadlogid" bigint,
     CONSTRAINT "qr_report_id_" PRIMARY KEY ("id_")
)WITH (oids = false);

