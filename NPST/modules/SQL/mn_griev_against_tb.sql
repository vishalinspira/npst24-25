DROP TABLE IF EXISTS "mn_griev_against_tb";
DROP SEQUENCE IF EXISTS mn_griev_against_tb_id_seq;
CREATE SEQUENCE mn_griev_against_tb_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."mn_griev_against_tb" (
    "id_" bigint DEFAULT nextval('mn_griev_against_tb_id_seq') NOT NULL,
		"sno"	integer	,
		"griev_raised_entity"	integer	,
		"token_no"	integer	,
		"griev_logged_date"	date	,
		"resolution_timestamp"	timestamp without time zone	,
		"griev_particulars"	character varying	,
		"griev_closure_tat"	integer	,
		"solution_provided"	character varying	,
		"tb_remarks"character varying	,
		"cra" character varying	,
    "createdate" date NOT NULL,
	"createdby" bigint NOT NULL,
	"reportuploadlogid" bigint,
    CONSTRAINT "mn_griev_against_tb_id_" PRIMARY KEY ("id_")
) WITH (oids = false);