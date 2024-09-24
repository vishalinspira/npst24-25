--DROP TABLE IF EXISTS "qr_payin_delay_exceed_25min";
--DROP SEQUENCE IF EXISTS qr_payin_delay_exceed_25min_id__seq1;
--CREATE SEQUENCE qr_payin_delay_exceed_25min_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
--
--CREATE TABLE public.qr_payin_delay_exceed_25min (
--	id_ bigint NOT NULL,
--	sno bigint,
--	date_ date,
--	payin_file_timestamp time without time zone,
--	fund_transfer_timestamp time without time zone,
--	tat_time time without time zone,
--	delay time without time zone,
--	auditee_comments character varying(1000),
--	cra character varying(10),
--	createdon date,
--	createdby bigint,
--	CONSTRAINT qr_payin_delay_exceed_25min_pkey PRIMARY KEY (id_)
--)WITH (oids = false);

DROP TABLE IF EXISTS "qr_payin_delay_exceed_25min";
DROP SEQUENCE IF EXISTS qr_payin_delay_exceed_25min_id__seq1;
CREATE SEQUENCE qr_payin_delay_exceed_25min_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.qr_payin_delay_exceed_25min (
	id_ bigint NOT NULL,
	sno bigint,
	date_ date,
	payin_file_timestamp time without time zone,
	fund_transfer_timestamp time without time zone,
	tat_time time without time zone,
	delay time without time zone,
	auditee_comments character varying,
	cra character varying,
	createdon date,
	createdby bigint,
	reportuploadlogid bigint,
	CONSTRAINT qr_payin_delay_exceed_25min_pkey PRIMARY KEY (id_)
)WITH (oids = false);