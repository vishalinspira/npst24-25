DROP TABLE IF EXISTS "final_upload_file";
DROP SEQUENCE IF EXISTS final_upload_file_id__seq1;
CREATE SEQUENCE final_upload_file_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.final_upload_file (
    id_ bigint NOT NULL,
    pensionfund character varying(100),
    inceptiondate character varying(20),
    aum character varying(20),
    subscribers character varying(20),
    nav character varying(20),
    return_1year character varying(20),
    return_3year character varying(20),
    return_5year character varying(20),
    return_7year character varying(20),
    return_10year character varying(20),
    returninception character varying(20),
    createdon date,
    createdby bigint,
    CONSTRAINT final_upload_file_pkey PRIMARY KEY (id_)
) WITH (oids = false);