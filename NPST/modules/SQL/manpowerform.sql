DROP TABLE IF EXISTS "manpowerform";

CREATE TABLE public.manpowerform (
	id_ bigint NOT NULL,
    reportuploadlogid bigint NOT NULL,
    mpreportfileid character varying,
    createdon date,
    createdby bigint
) WITH (oids = false);