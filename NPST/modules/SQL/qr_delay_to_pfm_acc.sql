--DROP TABLE IF EXISTS "qr_delay_to_pfm_acc";
--DROP SEQUENCE IF EXISTS qr_delay_to_pfm_acc_id__seq1;
--CREATE SEQUENCE qr_delay_to_pfm_acc_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
--
--CREATE TABLE public.qr_delay_to_pfm_acc (
--	id_ bigint NOT NULL,
--	sno bigint,
--	tid bigint,
--	amt numeric(15,2),
--	funds_receipt_date date,
--	transfer_date date,
--	frc_upload_date date,
--	delay character varying(50),
--	auditee_comment character varying(1000),
--	cra character varying(10),
--	createdon date,
--	createdby bigint,
--	CONSTRAINT qr_delay_to_pfm_acc_pkey PRIMARY KEY (id_)
--)WITH (oids = false);

DROP TABLE IF EXISTS "qr_delay_to_pfm_acc";
DROP SEQUENCE IF EXISTS qr_delay_to_pfm_acc_id__seq1;
CREATE SEQUENCE qr_delay_to_pfm_acc_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.qr_delay_to_pfm_acc (
	id_ bigint NOT NULL,
	sno bigint,
	tid bigint,
	amt numeric(15,2),
	funds_receipt_date date,
	transfer_date date,
	frc_upload_date date,
	delay character varying,
	auditee_comment character varying,
	cra character varying,
	createdon date,
	createdby bigint,
	reportuploadlogid bigint,
	CONSTRAINT qr_delay_to_pfm_acc_pkey PRIMARY KEY (id_)
)WITH (oids = false);