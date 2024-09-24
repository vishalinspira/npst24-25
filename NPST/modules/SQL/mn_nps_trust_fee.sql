
DROP TABLE IF EXISTS "mn_nps_trust_fee";
	DROP SEQUENCE IF EXISTS mn_nps_trust_fee_id__seq;
	CREATE SEQUENCE mn_nps_trust_fee_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

	CREATE TABLE "public"."mn_nps_trust_fee" (
		"id_" integer DEFAULT nextval('mn_nps_trust_fee_id__seq') NOT NULL,
		"pension_fund_name" character varying,
		"scheme_name" character varying,
		"date_" date,
    	"total_aum" numeric(15,2),
    	"nps_trust_fees" numeric(15,2),
			
		"createdate" date,
		"createdby" bigint,
		 "reportuploadlogid"  bigint
	) WITH (oids = false);
	
	
		