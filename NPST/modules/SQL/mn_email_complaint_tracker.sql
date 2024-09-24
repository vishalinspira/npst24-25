DROP TABLE IF EXISTS "mn_email_complaint_tracker";
DROP SEQUENCE IF EXISTS mn_email_complaint_tracker_id_seq;
CREATE SEQUENCE mn_email_complaint_tracker_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."mn_email_complaint_tracker" (
    "id_" bigint DEFAULT nextval('mn_email_complaint_tracker_id_seq') NOT NULL,
		"sno"	bigint,
		"receipt_date"	date,
		"from_"	character varying,
		"subject"	character varying,
		"gist_case"	character varying,
		"revert_containt"	character varying,
		"resolve_date"	date,
    "createdate" date NOT NULL,
	"createdby" bigint NOT NULL,
	"reportuploadlogid" bigint,
    CONSTRAINT "mn_email_complaint_tracker_id_" PRIMARY KEY ("id_")
) WITH (oids = false);