DROP TABLE IF EXISTS "reportuploadlogcra";
CREATE TABLE "public"."reportuploadlogcra" (
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
    "remarks" text
) WITH (oids = false);

ALTER TABLE "reportuploadlogcra"
ADD "statuskey" character varying NULL;
COMMENT ON TABLE "reportuploadlogcra" IS '';