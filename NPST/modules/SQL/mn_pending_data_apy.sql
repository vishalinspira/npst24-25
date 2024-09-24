DROP TABLE IF EXISTS "mn_pending_data_apy";
	DROP SEQUENCE IF EXISTS mn_pending_data_apy_id__seq;
	CREATE SEQUENCE mn_pending_data_apy_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

	CREATE TABLE "public"."mn_pending_data_apy" (
		"id_" integer DEFAULT nextval('mn_pending_data_apy_id__seq') NOT NULL,
		
		"sr_no"	integer,
		"token_number"	character varying NOT NULL,
		"pran_id_created_by"	bigint NOT NULL,
		"current_dates"	date,
		"status"	character varying,
		"grievance_logging_date"	date,
		"raised_by"	character varying,
		"nlao_reg_no"	character varying NOT NULL,
		"nlao_name"	character varying,
		"nloo_reg_no"	character varying NOT NULL,
		"nloo_name"	character varying,
		"sector"	character varying,
		"broad_category"	character varying,
		"grievance_text"	character varying,
		"followup_action_1"	date,
		"followup_action_2"	date,
		"cra"	character varying,	
		"createdate" date,
		"createdby" bigint,
		"reportuploadlogid" bigint NULL
	) WITH (oids = false);
	
	
		