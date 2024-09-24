DROP TABLE IF EXISTS "reportuploadloggrievampfrda";
CREATE TABLE "public"."reportuploadloggrievampfrda" (
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