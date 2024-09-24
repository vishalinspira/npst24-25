DROP TABLE IF EXISTS "qr_rpt_submission";
DROP SEQUENCE IF EXISTS qr_rpt_submission_id__seq1;
CREATE SEQUENCE qr_rpt_submission_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.qr_rpt_submission (
	id_ bigint NOT NULL,
	sno bigint,
	month_ character varying(50),
	name_of_report character varying(50),
	mode_ character varying(50),
	periodicity character varying(50),
	_1st_week_remark character varying(100),
	_2nd_week_remark character varying(100),
	_3rd_week_remark character varying(100),
	_4th_week_remark character varying(100),
	_5th_week_remark character varying(100),
	auditee_comments character varying(1000),
	cra character varying(10),
	createdon date,
	createdby bigint,
	reportuploadlogid bigint,
	CONSTRAINT qr_rpt_submission_pkey PRIMARY KEY (id_)
)WITH (oids = false);