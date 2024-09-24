DROP TABLE IF EXISTS "qr_cause_wise_pattern_analysis";
DROP SEQUENCE IF EXISTS qr_cause_wise_pattern_analysis_id__seq1;
CREATE SEQUENCE qr_cause_wise_pattern_analysis_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE IF NOT EXISTS public.qr_cause_wise_pattern_analysis
(
    id_ bigint NOT NULL,
    cra character varying,
	year character varying,
	quarter character varying,
	particulars character varying,
	present_quarter_death numeric(8,2), 
	present_quarter_premature numeric(8,2),
	present_quarter_superannuation numeric(8,2),
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT qr_cause_wise_pattern_analysis_pkey PRIMARY KEY (id_)
) WITH (oids = false);