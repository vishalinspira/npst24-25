
DROP TABLE IF EXISTS "reportuploadloggrievances";
CREATE TABLE "public"."reportuploadloggrievances" (
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
	"statuskey" character varying NULL;
) WITH (oids = false);


ALTER TABLE "reportuploadloggrievances"
ADD "statuskey" character varying NULL;
COMMENT ON TABLE "reportuploadloggrievances" IS '';