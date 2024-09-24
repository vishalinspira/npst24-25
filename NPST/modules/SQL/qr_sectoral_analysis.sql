DROP TABLE IF EXISTS "qr_sectoral_analysis";
DROP SEQUENCE IF EXISTS qr_sectoral_analysis_id__seq1;
CREATE SEQUENCE qr_sectoral_analysis_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE IF NOT EXISTS public.qr_sectoral_analysis
(
    id_ bigint NOT NULL,
    year character varying,
    quarter character varying,
    cra character varying,
	sectoral_analysis character varying,
	central_government numeric(8,2),
	state_government numeric(8,2),
	corporate numeric(8,2),
	uos numeric(8,2),
	nps_lite numeric(8,2),
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT qr_sectoral_analysis_pkey PRIMARY KEY (id_)
) WITH (oids = false);