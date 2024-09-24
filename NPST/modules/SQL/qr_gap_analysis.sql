DROP TABLE IF EXISTS "qr_gap_analysis";
DROP SEQUENCE IF EXISTS qr_gap_analysis_id__seq1;
CREATE SEQUENCE qr_gap_analysis_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE IF NOT EXISTS public.qr_gap_analysis
(
    id_ bigint NOT NULL,
	year character varying,
	quarter character varying,
	parameters character varying,
	cra character varying,
	no_of_subscribers numeric(8,2),
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT qr_gap_analysis_pkey PRIMARY KEY (id_)
) WITH (oids = false);