	
	DROP TABLE IF EXISTS "mn_escalated_to_nps_trust";
	DROP SEQUENCE IF EXISTS mn_escalated_to_nps_trust_id__seq;
	CREATE SEQUENCE mn_escalated_to_nps_trust_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

	CREATE TABLE "public"."mn_escalated_to_nps_trust" (
		"id_" integer DEFAULT nextval('mn_escalated_to_nps_trust_id__seq') NOT NULL,
		
		
		"scheme_name" character varying,
		"date_" date NOT NULL,
		"grievance_category" character varying,
		"griev_outstanding_at_month_end" integer,
		"zero_seven" integer,
		"eight_fifteen" integer,
		"sixteen_thirtyone" integer,
		"thirtytwo_ninety" integer,
		"ninetyone_oneeighty" integer,
		"oneeighttwo_threesixtyfive" integer,
		"morethan_threesixtysix" integer,
			
		"createdate" date,
		"createdby" bigint,
		"reportuploadlogid" bigint NULL
	) WITH (oids = false);
	
	
	
	
	
		