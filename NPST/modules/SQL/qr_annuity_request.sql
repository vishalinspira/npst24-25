DROP TABLE IF EXISTS "qr_annuity_request";
DROP SEQUENCE IF EXISTS qr_annuity_request_id__seq1;
CREATE SEQUENCE qr_annuity_request_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE IF NOT EXISTS public.qr_annuity_request
(
    id_ bigint NOT NULL,
    cra character varying,
	year character varying,
	quarter character varying,
	annuity_request_category character varying,
	cg numeric(8,2),
	sg numeric(8,2),
	corporate numeric(8,2),
	uos numeric(8,2),
	nps_lite numeric(8,2),
	total numeric(8,2),
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT qr_annuity_request_pkey PRIMARY KEY (id_)
) WITH (oids = false);