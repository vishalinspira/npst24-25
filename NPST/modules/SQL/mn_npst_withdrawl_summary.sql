DROP TABLE IF EXISTS "mn_npst_withdrawl_summary";
	DROP SEQUENCE IF EXISTS mn_npst_withdrawl_summary_id__seq;
	CREATE SEQUENCE mn_npst_withdrawl_summary_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

	CREATE TABLE "public"."mn_npst_withdrawl_summary" (
		"id_" integer DEFAULT nextval('mn_npst_withdrawl_summary_id__seq') NOT NULL,
		
		
		"sno"	integer NOT NULL,
		"particulars"	character varying,
		"amt"numeric(15,2),
		"month" date,
		"cra"character varying	not null,		
			
		"createdate" date,
		"createdby" bigint,
		"reportuploadlogid" bigint
	) WITH (oids = false);
	
	