DROP TABLE IF EXISTS "intrst_of_direc_in_other_comp";
DROP SEQUENCE IF EXISTS intrst_of_direc_in_other_comp_id__seq1;
CREATE SEQUENCE intrst_of_direc_in_other_comp_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.intrst_of_direc_in_other_comp (
	id_ bigint not null,
	nameCompanyFirmAssociation character varying,
	natureInterestConcernInterest character varying,
    shareholding character varying,
    dateOnInterestOrConcernArose date,
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    
    CONSTRAINT intrst_of_direc_in_other_comp_id__seq1_pkey PRIMARY KEY (id_)
) WITH (oids = false);