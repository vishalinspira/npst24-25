DROP TABLE IF EXISTS "qr_no_of_resolutions";
DROP SEQUENCE IF EXISTS qr_no_of_resolutions_id__seq1;
CREATE SEQUENCE qr_no_of_resolutions_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE IF NOT EXISTS public.qr_no_of_resolutions
(
    id_ bigint NOT NULL,
	sno numeric(15,2),
	company_name character varying,
	meeting_date date,
	resolutions numeric,
	resolution_details character varying,
    createdate date,
    createdby bigint,
    CONSTRAINT qr_no_of_resolutions_pkey PRIMARY KEY (id_)
) WITH (oids = false);