DROP TABLE IF EXISTS "mn_summary_grievance";
	DROP SEQUENCE IF EXISTS mn_summary_grievance_id__seq;
	CREATE SEQUENCE mn_summary_grievance_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

	CREATE TABLE "public"."mn_summary_grievance" (
		"id_" integer DEFAULT nextval('mn_summary_grievance_id__seq') NOT NULL,
		
		
		"name_of_ministry_central_gov"	character varying,
		"date_"	date,
		"reported_referrals"	integer,
		"sixty_oneeighty"	integer,
		"oneeighty_threesixtyfive"	integer,
		"morethan_threesixtyfive"	integer,
		"cra"	character varying,
		"sector_type"	character varying,
			
			
		"createdate" date,
		"createdby" bigint,
		"reportuploadlogid" bigint NULL
	) WITH (oids = false);
	
		
    
ALTER TABLE "mn_summary_grievance"
ADD "reportuploadlogid" bigint NULL;