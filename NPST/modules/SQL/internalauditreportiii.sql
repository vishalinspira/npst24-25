DROP TABLE IF EXISTS "internalauditreportiii";
DROP SEQUENCE IF EXISTS internalauditreportiii_id__seq1;
CREATE SEQUENCE internalauditreportiii_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.internalauditreportiii
(
    id_ bigint NOT NULL,
    detailsofsubmissionof_monthly character varying COLLATE pg_catalog."default",
    concernedperiod character varying COLLATE pg_catalog."default",
    disclosuredate date,
    duedatedisclosure date,
    delay date,
    createdate date,
    createdby bigint
)
WITH (oids = false);