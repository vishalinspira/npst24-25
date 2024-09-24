DROP TABLE IF EXISTS "mpf_sec1";

CREATE TABLE public.mpf_sec1 (
	manpowersec1id bigint NOT NULL,
    reportuploadlogid bigint NOT NULL,
    destination character varying,
    name character varying,
    dateofappoinment date,
    contactno bigint,
    email character varying,
    highestqualification character varying,
    totalprofexp bigint,
    deputation character varying,
    linkedinid bigint,
    position character varying,
    dateofboardmeetngapprove date,
    biodata bigint
) WITH (oids = false);