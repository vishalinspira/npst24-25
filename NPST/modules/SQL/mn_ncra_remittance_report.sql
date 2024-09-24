DROP TABLE IF EXISTS "mn_ncra_remittance_report";
	DROP SEQUENCE IF EXISTS mn_ncra_remittance_report_id__seq;
	CREATE SEQUENCE mn_ncra_remittance_report_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

	CREATE TABLE "public"."mn_ncra_remittance_report" (
		"id_" integer DEFAULT nextval('mn_ncra_remittance_report_id__seq') NOT NULL,
		
		"remittence_cat"	character varying,
		"timelines"	character varying,
		"no_of_instances"	integer,
		"amt_transferred"	numeric(15,2),
		"cra" character varying	not null,
        "month" timestamp not null,
			
			
		"createdate" date,
		"createdby" bigint,
		"reportuploadlogid" bigint
	) WITH (oids = false);
	
ALTER TABLE "mn_ncra_remittance_report"
ADD "month" timestamp;
COMMENT ON TABLE "mn_ncra_remittance_report" IS '';