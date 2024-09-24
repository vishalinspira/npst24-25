DROP TABLE IF EXISTS "asp_report";
DROP SEQUENCE IF EXISTS asp_report_id_seq;
CREATE SEQUENCE asp_report_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."asp_report" (
    "id_" bigint DEFAULT nextval('asp_report_id_seq') NOT NULL,
		"section"	character varying	,
		"particulars"	character varying	,
		"value"	character varying	,
		"entity_name"	character varying	,
		"month_" date ,
		
    "createdate" date ,
	"createdby" bigint ,
	"reportuploadlogid" bigint,
    CONSTRAINT "asp_report_id_" PRIMARY KEY ("id_")
) WITH (oids = false);