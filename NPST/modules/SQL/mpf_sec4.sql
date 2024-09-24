DROP TABLE IF EXISTS "mpf_sec4";

CREATE TABLE public.mpf_sec4 (
    manpowersec4id bigint NOT NULL,
    reportuploadlogid bigint NOT NULL,
    namesofthecompanies character varying,
    natureofinterest character varying,
    shareholding character varying,
    dateoninterestorconcernarose date
) WITH (oids = false);