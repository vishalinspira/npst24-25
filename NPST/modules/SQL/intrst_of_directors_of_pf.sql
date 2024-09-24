DROP TABLE IF EXISTS "intrst_of_directors_of_pf";
DROP SEQUENCE IF EXISTS intrst_of_directors_of_pf_id__seq1;
CREATE SEQUENCE intrst_of_directors_of_pf_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.intrst_of_directors_of_pf(
	id_ bigint not null,
	nameOfDirector character varying,
	dateSubmissionFormMbDirector date,
    fileEntryId bigint,
    createdon date,
    createdby bigint,
    reportuploadlogid bigint ,
    CONSTRAINT intrst_of_directors_of_pf_pkey PRIMARY KEY (id_)
) WITH (oids = false);