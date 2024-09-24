DROP TABLE IF EXISTS "qr_ses_vote_matrix";
DROP SEQUENCE IF EXISTS qr_ses_vote_matrix_id__seq1;
CREATE SEQUENCE qr_ses_vote_matrix_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE IF NOT EXISTS public.qr_ses_vote_matrix
(
    id_ bigint NOT NULL,
	company_name character varying,
	isin character varying,
	meet_date date,
	meeting_type character varying,
	evoting_platform character varying,
	resolution_sr_no numeric(15,2),
	resolution_type character varying,
	resolution_dtls character varying,
	ses_recomm character varying,
	rationale character varying,
	lic_pf character varying,
	uti_pf character varying,
	sbi_pf character varying,
	hdfc_pf character varying,
	icici_pf character varying,
	kotak_pf character varying,
	birla_pf character varying,
	majority_vote character varying,
    createdate date,
    createdby bigint,
    CONSTRAINT qr_ses_vote_matrix_pkey PRIMARY KEY (id_)
) WITH (oids = false);