DROP TABLE IF EXISTS "email_complaint_tracker";
DROP SEQUENCE IF EXISTS email_complaint_tracker_id__seq;
CREATE SEQUENCE email_complaint_tracker_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."email_complaint_tracker" (
    "id_" integer DEFAULT nextval('email_complaint_tracker_id__seq') NOT NULL,
    "sno" bigint,
	"receipt_date" character varying  NULL,
	"from" character varying  NULL,
	"subject" character varying  NULL,
	"gist_case" character varying  NULL,
	"revert_containt" character varying  NULL,
	"resolve_date" character varying  NULL,
    "createdate" date,
    "createdby" bigint
) WITH (oids = false);

