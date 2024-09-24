DROP TABLE IF EXISTS "reportuploadfilelog";
DROP SEQUENCE IF EXISTS reportuploadfilelog_reportUploadLogId_seq1;
CREATE SEQUENCE reportuploadfilelog_reportUploadLogId_seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.reportuploadfilelog (
    reportUploadfilelogid integer NOT NULL,
    
    reportUploadLogId bigint,
    fileEntryId bigint,
    
	
	createdate date NOT NULL,
	createdby bigint NOT NULL,
	
	signature character varying NULL,
	filelist character varying NULL,
	
	
	CONSTRAINT reportuploadfilelog_pkey PRIMARY KEY (reportUploadLogId)
) WITH (oids = false);

ALTER TABLE "reportuploadfilelog"
ADD "signature" character varying NULL;
COMMENT ON TABLE "reportuploadfilelog" IS '';


ALTER TABLE "reportuploadfilelog"
ADD "filelist" character varying NULL;
COMMENT ON TABLE "reportuploadfilelog" IS '';