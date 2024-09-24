--DROP TABLE IF EXISTS "qr_frc_delay_after_0930am";
--DROP SEQUENCE IF EXISTS qr_frc_delay_after_0930am_id__seq1;
--CREATE SEQUENCE qr_frc_delay_after_0930am_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
--
--CREATE TABLE public.qr_frc_delay_after_0930am (
--	id_ bigint NOT NULL,
--	sno bigint,
--	date_of_upload date,
--	file_name character varying(50),
--	tat_time time without time zone,
--	time_stamp time without time zone,
--	delay time without time zone,
--	auditee_comments character varying(1000),
--	cra character varying(10),
--	createdon date,
--	createdby bigint,
--	CONSTRAINT qr_frc_delay_after_0930am_pkey PRIMARY KEY (id_)
--)WITH (oids = false);

DROP TABLE IF EXISTS "qr_frc_delay_after_0930am";
DROP SEQUENCE IF EXISTS qr_frc_delay_after_0930am_id__seq1;
CREATE SEQUENCE qr_frc_delay_after_0930am_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.qr_frc_delay_after_0930am (
	id_ bigint NOT NULL,
	sno bigint,
	date_of_upload date,
	file_name character varying,
	tat_time time without time zone,
	time_stamp time without time zone,
	delay time without time zone,
	auditee_comments character varying,
	cra character varying,
	createdon date,
	createdby bigint,
	reportuploadlogid bigint,
	CONSTRAINT qr_frc_delay_after_0930am_pkey PRIMARY KEY (id_)
)WITH (oids = false);