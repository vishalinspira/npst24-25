DROP TABLE IF EXISTS "weekly_repo_eq";
DROP SEQUENCE IF EXISTS weekly_repo_eq_id__seq1;
CREATE SEQUENCE weekly_repo_eq_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."weekly_repo_eq" (
	"id_" integer DEFAULT nextval('weekly_repo_eq_id__seq1') NOT NULL,
    "date_" date,
    "fin" character varying,
    "scheme"  character varying,
    "company_name" character varying,
    "security_code" character varying,
    "isin" character varying,
    "secdesc" character varying,
    "sec" character varying,
    "coupon" character varying,
    "faceval" bigint,
    "custhold" numeric,
    "transit" numeric,
    "loghold" numeric,
    "bse_rate" numeric,
    "bse_value" numeric,
    "nse_rate" numeric,
    "nse_value" numeric, 
    "createdate" date,
    "createdby" bigint,
    "reportuploadlogid" bigint
    
) WITH (oids = false);

