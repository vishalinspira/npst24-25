DROP TABLE IF EXISTS "reportuploadlogmaker";
CREATE TABLE "public"."reportuploadlogmaker" (
    "reportuploadlogid" bigint NOT NULL,
    "reportmasterid" bigint,
    "reportdate" timestamp,
    "createdate" timestamp,
    "createdby" bigint,
    "fileentryid" bigint,
    "uploaded" boolean,
    "modifyby" bigint,
    "modifydate" timestamp,
    "status" integer,
    "statusbyuserid" bigint,
    "statusbyusername" character varying,
    "statusdate" timestamp,
    "uuid_" character varying,
    "workflowcontext" text,
    "remarks" text,
	"statuskey" character varying NULL
) WITH (oids = false);

ALTER TABLE "reportuploadlogmaker"
ADD "statuskey" character varying NULL;
COMMENT ON TABLE "reportuploadlogmaker" IS '';