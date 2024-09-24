DROP TABLE IF EXISTS "mn_top_return_reason";
	DROP SEQUENCE IF EXISTS mn_top_return_reason_id__seq;
	CREATE SEQUENCE mn_top_return_reason_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

	CREATE TABLE "public"."mn_top_return_reason" (
		"id_" integer DEFAULT nextval('mn_top_return_reason_id__seq') NOT NULL,
		
		
		"sno"	integer,
		"reasons"	character varying,
		"no_of_remittances"	integer	,
		"month" date not null,
		"cra"character varying	not null,
		"createdate" date,
		"createdby" bigint,
		"reportuploadlogid" bigint NULL
	) WITH (oids = false);