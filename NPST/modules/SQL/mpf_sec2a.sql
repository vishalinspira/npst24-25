DROP TABLE IF EXISTS "mpf_sec2a";

CREATE TABLE public.mpf_sec2a (
    manpowersec2aid bigint NOT NULL,
    reportuploadlogid bigint NOT NULL,
    designation character varying,
    name character varying,
    chairperson_member character varying,
    dateofappointmentinthecommitte date
) WITH (oids = false);