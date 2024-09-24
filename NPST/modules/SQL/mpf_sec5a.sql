DROP TABLE IF EXISTS "mpf_sec5a";

CREATE TABLE public.mpf_sec5a (
    manpowersec5abid bigint NOT NULL,
    reportuploadlogid bigint NOT NULL,
    designation character varying,
    name character varying,
    chairperson_member character varying,
    dateofappointmentinthecommitte date
) WITH (oids = false);