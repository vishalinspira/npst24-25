DROP TABLE IF EXISTS "mpf_sec5b";

CREATE TABLE public.mpf_sec5b (
    manpowersec5bid bigint NOT NULL,
    reportuploadlogid bigint NOT NULL,
    designation character varying,
    name character varying,
    chairperson_member character varying,
    dateofappointmentinthecommitte date
) WITH (oids = false);