DROP TABLE IF EXISTS "mn_return_remittance_ageing";
	DROP SEQUENCE IF EXISTS mn_return_remittance_ageing_id__seq;
	CREATE SEQUENCE mn_return_remittance_ageing_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

	CREATE TABLE "public"."mn_return_remittance_ageing" (
		"id_" integer DEFAULT nextval('mn_return_remittance_ageing_id__seq') NOT NULL,
		
		
		"period"	character varying,
		"no_of_remittances"	integer,
		"amt"	numeric(15,2),
		"month" date not null,
		"cra"character varying	not null,		
			
		"createdate" date,
		"createdby" bigint,
		"reportuploadlogid" bigint NULL
	) WITH (oids = false);