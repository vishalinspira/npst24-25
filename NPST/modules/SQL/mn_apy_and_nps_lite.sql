DROP TABLE IF EXISTS "mn_apy_and_nps_lite";
	DROP SEQUENCE IF EXISTS mn_apy_and_nps_lite_id__seq;
	CREATE SEQUENCE mn_apy_and_nps_lite_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

	CREATE TABLE "public"."mn_apy_and_nps_lite" (
		"id_" integer DEFAULT nextval('mn_apy_and_nps_lite_id__seq') NOT NULL,
		
		
		"sr_no"	integer,
		"token_number"	character varying NOT NULL,
		"pran"	integer NOT NULL,
		"subscriber_name"	character varying,
		"date_"	date,
		"status"	character varying,
		"grievance_logging_date"	date,
		"date_considered_for_ageing"	date,
		"ageing"	integer, 
		"bucket"	character varying,
		"raised_by"	character varying,
		"nlao_reg_no"	character varying,
		"nlao_name"	character varying,
		"nloo_reg_no"	character varying NOT NULL,
		"nloo_name"	character varying,
		"sector"	character varying,
		"broad_category"	character varying,
		"grievance_text"	character varying,
		"followup_action_i"	date,
		"followup_action_ii"	date,
		"cra"	character varying,
    
			
			
		"createdate" date,
		"createdby" bigint,
		"reportuploadlogid" bigint NULL
	) WITH (oids = false);
	
		
    
