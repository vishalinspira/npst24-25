DROP TABLE IF EXISTS "mpf_sec5";

CREATE TABLE public.mpf_sec5 (
    manpowersec5id bigint NOT NULL,
    reportuploadlogid bigint NOT NULL,
    iccstatusi character varying,
    rmccstatusi character varying
) WITH (oids = false);