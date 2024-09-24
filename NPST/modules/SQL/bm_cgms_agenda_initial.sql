DROP TABLE IF EXISTS "bm_cgms_agenda_initial";
DROP SEQUENCE IF EXISTS bm_cgms_agenda_initial_id__seq1;
CREATE SEQUENCE bm_cgms_agenda_initial_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.bm_cgms_agenda_initial (
    id_ bigint NOT NULL,
    b_number character varying(50),
    item_number character varying(50),
    date_1 character varying(10),
    date_2 character varying(10),
    date_3 character varying(10),
    createdon date,
    createdby bigint,
    CONSTRAINT bm_cgms_agenda_initial_pkey PRIMARY KEY (id_)
)WITH (oids = false);