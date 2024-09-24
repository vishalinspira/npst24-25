
DROP TABLE IF EXISTS "reportuploadlog";
CREATE TABLE "public"."reportuploadlog" (
    "reportuploadlogid" bigint NOT NULL,
    "reportmasterid" bigint,
    "reportdate" timestamp,
    "createdate" timestamp,
    "createdby" bigint,
    "fileentryid" bigint,
    "uploaded" boolean,
    "uploaded_i" integer,
	"intermediaryname" character varying NULL,
	"intermediaryid" bigint NULL,
	 "status_" character varying NULL
) WITH (oids = false);


ALTER TABLE "reportuploadlog"
ADD "intermediaryname" character varying NULL;
COMMENT ON TABLE "reportuploadlog" IS '';

ALTER TABLE "reportuploadlog"
ADD "intermediaryid" bigint NULL;
COMMENT ON TABLE "reportuploadlog" IS '';

ALTER TABLE "reportuploadlog"
ADD "status_" character varying NULL;
COMMENT ON TABLE "reportuploadlog" IS '';
