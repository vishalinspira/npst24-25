DROP TABLE IF EXISTS "qr_annuity_request_ageing";
DROP SEQUENCE IF EXISTS qr_annuity_request_ageing_id__seq1;
CREATE SEQUENCE qr_annuity_request_ageing_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE IF NOT EXISTS public.qr_annuity_request_ageing
(
    id_ bigint NOT NULL,
    cra character varying,
	year character varying,
	quarter character varying,
	requests_cause character varying,
	less_than_1_month integer,
	between_1_3_months integer,
	between_3_4_months integer,
	between_4_6_months integer,
	between_6_12_months integer,
	more_than_a_year integer,
	total integer,
    createdon date,
    createdby bigint,
    CONSTRAINT qr_annuity_request_ageing_pkey PRIMARY KEY (id_)
) WITH (oids = false);