--DROP TABLE IF EXISTS "qr_return_of_rejected_funds";
--DROP SEQUENCE IF EXISTS qr_return_of_rejected_funds_id__seq1;
--CREATE SEQUENCE qr_return_of_rejected_funds_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
--
--CREATE TABLE public.qr_return_of_rejected_funds (
--	id_ bigint NOT NULL,
--	sno bigint,
--	date_of_receipt date,
--	amount numeric(15,2),
--	nps_acc_name character varying(50),
--	date_of_return date,
--	return_reason character varying(50),
--	delay_reason character varying(50),
--	cra character varying(10),
--	createdon date,
--	createdby bigint,
--	CONSTRAINT qr_return_of_rejected_funds_pkey PRIMARY KEY (id_)
--)WITH (oids = false);

DROP TABLE IF EXISTS "qr_return_of_rejected_funds";
DROP SEQUENCE IF EXISTS qr_return_of_rejected_funds_id__seq1;
CREATE SEQUENCE qr_return_of_rejected_funds_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.qr_return_of_rejected_funds (
	id_ bigint NOT NULL,
	sno bigint,
	date_of_receipt date,
	amount numeric(15,2),
	nps_acc_name character varying,
	date_of_return date,
	return_reason character varying,
	delay_reason character varying,
	cra character varying,
	createdon date,
	createdby bigint,
	reportuploadlogid bigint,
	CONSTRAINT qr_return_of_rejected_funds_pkey PRIMARY KEY (id_)
)WITH (oids = false);