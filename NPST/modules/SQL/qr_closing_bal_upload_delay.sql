--DROP TABLE IF EXISTS "qr_closing_bal_upload_delay";
--DROP SEQUENCE IF EXISTS qr_closing_bal_upload_delay_id__seq1;
--CREATE SEQUENCE qr_closing_bal_upload_delay_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
--
--CREATE TABLE public.qr_closing_bal_upload_delay (
--	id_ bigint NOT NULL,
--	sno bigint,
--	file_name character varying(50),
--	uploaded_by character varying(50),
--	uploaded_date date,
--	uploaded_time timestamp without time zone,
--	tat time without time zone,
--	delay time without time zone,
--	status character varying(50),
--	cra character varying(10),
--	auditees_comment character varying(1000),
--	createdon date,
--	createdby bigint,
--	CONSTRAINT qr_closing_bal_upload_delay_pkey PRIMARY KEY (id_)
--)WITH (oids = false);

DROP TABLE IF EXISTS "qr_closing_bal_upload_delay";
DROP SEQUENCE IF EXISTS qr_closing_bal_upload_delay_id__seq1;
CREATE SEQUENCE qr_closing_bal_upload_delay_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.qr_closing_bal_upload_delay (
	id_ bigint NOT NULL,
	sno bigint,
	file_name character varying,
	uploaded_by character varying,
	uploaded_date date,
	uploaded_time timestamp without time zone,
	tat time without time zone,
	delay time without time zone,
	status character varying,
	cra character varying,
	auditees_comment character varying,
	createdon date,
	createdby bigint,
	CONSTRAINT qr_closing_bal_upload_delay_pkey PRIMARY KEY (id_)
)WITH (oids = false);