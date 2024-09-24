DROP TABLE IF EXISTS "custodiancompform";

	CREATE TABLE "public"."custodiancompform" (
		"reportuploadlogid" integer NOT NULL,
		
		"formdate" date,
		"qcfile_id" bigint,
		"remarks_i_i"  character varying,
		"remarks_i_ii"  character varying,
		"remarks_ii"  character varying,
		"remarks_iii"  character varying,
		"remarks_iv"  character varying,
		"remarks_v"  character varying,
		"remarks_vi"  character varying,
		"remarks_vii"  character varying,
		"remarks_viii"  character varying,
		"remarks_ix"  character varying,
		"remarks_x"  character varying,
		"remarks_xi"  character varying,
		"remarks_xii"  character varying,
		"remarks_xiii"  character varying,
		"month"  character varying,
		"signature"  character varying,
		"employeename"  character varying,
		"designation"  character varying,
		"date_3"  character varying,
		"place"  character varying,
		"reportmasterid" bigint,
		"reportdate" date,
		
		
		"createdate" timestamp,
		"createdby"  bigint,
		"modifyby"  bigint,
		"modifydate" date,
		
		
		"status" integer,
		"statusbyuserid" bigint,
		"statusbyusername" character varying,
		"statusdate" timestamp,
		"uuid_" character varying,
		"workflowcontext" character varying,
		"remarks" character varying
		
) WITH (oids = false);
	
	
	ALTER TABLE "custodiancompform"
ADD "formdate" date NULL;
COMMENT ON TABLE "custodiancompform" IS '';

ALTER TABLE "custodiancompform"
ADD "fileentryid" bigint NULL;
COMMENT ON TABLE "custodiancompform" IS '';
	
		