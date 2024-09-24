DROP TABLE IF EXISTS "mn_ministry_wise_pending";
DROP SEQUENCE IF EXISTS mn_ministry_wise_pending_id__seq1;
CREATE SEQUENCE mn_ministry_wise_pending_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE IF NOT EXISTS public.mn_ministry_wise_pending
(
    id_ bigint NOT NULL,
    name_of_the_ministry character varying COLLATE pg_catalog."default",
    date_ date,
    outstanding_end_of_month integer,
    _0_to_7_days integer,
    _8_to_15_days integer,
    _16_to_31_days integer,
    _32_to_90_days integer,
    _91_to_180_days integer,
    _181_to_365_days integer,
    more_than_366_days integer,
    category character varying NOT NULL,
    cra character varying(1000) COLLATE pg_catalog."default",
    createdate date,
    createdby bigint,
    reportuploadlogid bigint NULL,
    CONSTRAINT mn_ministry_wise_pending_pkey PRIMARY KEY (id_)
) WITH (oids = false);