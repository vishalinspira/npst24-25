DROP TABLE IF EXISTS "reportuploadlogpfmcustodian";
CREATE TABLE "public"."reportuploadlogpfmcustodian" (
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


ALTER TABLE "reportuploadlogpfmcustodian"
ADD "statuskey" character varying NULL;
COMMENT ON TABLE "reportuploadlogpfmcustodian" IS '';