DROP TABLE IF EXISTS "mn_pfm_aum";
	DROP SEQUENCE IF EXISTS mn_pfm_aum_id__seq;
	CREATE SEQUENCE mn_pfm_aum_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

	CREATE TABLE "public"."mn_pfm_aum" (
		"id_" integer DEFAULT nextval('mn_pfm_aum_id__seq') NOT NULL,
		
		
		"date" character varying,
		"pension_fund_name"	character varying,
		"scheme_cg"	numeric,
		"scheme_sg"	numeric,
		"scheme_e_i"	numeric,
		"scheme_c_i"	numeric,
		"scheme_g_i"	numeric,
		"scheme_a_i"	numeric,
		"scheme_e_ii"	numeric,
		"scheme_c_ii"	numeric,
		"scheme_g_ii"	numeric,
		"scheme_tax_saver_tier_ii"	numeric,
		"scheme_nps_lite"	numeric,
		"scheme_apy"	numeric,
		"scheme_corp_cg"	numeric,
		"grand_total"	numeric,
		"cra"	character varying,
			
			
		"createdate" date,
		"createdby" bigint
	) WITH (oids = false);
	
	
		