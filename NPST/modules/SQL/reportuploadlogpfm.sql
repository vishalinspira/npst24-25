

DROP TABLE IF EXISTS "reportuploadlogpfm";
CREATE TABLE "public"."reportuploadlogpfm" (
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
    "statuskey" bigint
) WITH (oids = false);

ALTER TABLE "reportuploadlogpfm"
ADD "statuskey" character varying NULL;
COMMENT ON TABLE "reportuploadlogpfm" IS '';