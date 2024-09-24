
DROP TABLE IF EXISTS "list_of_branches";
DROP SEQUENCE IF EXISTS list_of_branches_id__seq;
CREATE SEQUENCE list_of_branches_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."list_of_branches" (
    "id_" integer DEFAULT nextval('list_of_branches_id__seq') NOT NULL,
    "sno" bigint,
	"branch_code" character varying  NULL,
	"date_of_opening" character varying  NULL,
	"branch_name" character varying  NULL,
	"address" character varying  NULL,
	"district" character varying  NULL,
	"state_ut" character varying  NULL,
	"centre_asper_census2011" character varying  NULL,
	"whether_relocated" character varying  NULL,
	"upgraded_relocation" character varying  NULL,
	"relocation_date" character varying  NULL,
	"uniformcode_i" character varying  NULL,
	"uniformcode_ii" character varying  NULL,
	"board_line_no" character varying  NULL,
	"fax_no" character varying  NULL,
	"mobile_no" bigint,
    "createdate" date,
    "createdby" bigint,
    "reportuploadlogid" bigint
) WITH (oids = false);

