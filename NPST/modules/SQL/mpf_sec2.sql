DROP TABLE IF EXISTS "mpf_sec2";

CREATE TABLE public.mpf_sec2 (
	manpowersec2id bigint NOT NULL,
    reportuploadlogid bigint NOT NULL,
    iccstatus character varying,
    rmccstatus character varying
) WITH (oids = false);