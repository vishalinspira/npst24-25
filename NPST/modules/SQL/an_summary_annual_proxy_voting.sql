DROP TABLE IF EXISTS "an_summary_annual_proxy_voting";
	DROP SEQUENCE IF EXISTS an_summary_annual_proxy_voting_id__seq;
	CREATE SEQUENCE an_summary_annual_proxy_voting_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

	CREATE TABLE "public"."an_summary_annual_proxy_voting" (
		"id_" integer DEFAULT nextval('an_summary_annual_proxy_voting_id__seq') NOT NULL,
		
		
		"sr_no"	numeric,
		"pension_fund_name"	character varying,
		"year"	character varying,
		"quarter"	character varying,
		"total_number_of_resolution"	numeric,
		"for_"	numeric,
		"against"	numeric,
		"abstained"	numeric,
			
		"createdate" date,
		"createdby" bigint
	) WITH (oids = false);
	
	
	
		