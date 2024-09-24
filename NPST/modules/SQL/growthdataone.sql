DROP TABLE IF EXISTS "growthdataone";
	DROP SEQUENCE IF EXISTS growthdataone_id__seq;
	CREATE SEQUENCE growthdataone_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

	CREATE TABLE "public"."growthdataone" (
		"id_" integer DEFAULT nextval('growthdataone_id__seq') NOT NULL,
		
		
		"date_" character varying,
    	"pension_fund_name" character varying,
    	"scheme_cg" character varying,
    	"scheme_sg" character varying,
    	"scheme_e_i" character varying,
    	"scheme_c_i" character varying,
    	"scheme_g_i" character varying,
    	"scheme_a_i" character varying,
    	"scheme_e_ii" character varying,
    	"scheme_c_ii" character varying,
    	"scheme_g_ii" character varying,
    	"scheme_tax_saver_tier_ii" character varying,
    	"scheme_nps_lite" character varying,
    	"scheme_api" character varying,
    	"scheme_crop_cg" character varying,
    	"grand_total" character varying,
			
			
		"createdate" date,
		"createdby" bigint
	) WITH (oids = false);
	
	
		